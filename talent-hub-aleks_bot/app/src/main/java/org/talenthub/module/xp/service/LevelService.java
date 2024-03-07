package org.talenthub.module.xp.service;

import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.talenthub.module.xp.entity.Level;
import org.talenthub.module.xp.entity.PlayerLevel;
import org.talenthub.module.xp.repository.LevelRepository;
import org.talenthub.service.ConfigService;
import org.talenthub.service.MessageBroadcasterService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LevelService {

    private final PlayerLevelService playerLevelService;
    private final LevelRepository levelRepository;
    private final MessageBroadcasterService messageBroadcasterService;
    private final ConfigService configService;
    private final Logger LOGGER = LoggerFactory.getLogger(LevelService.class);

    @Transactional
    public void addXp(final GuildMessageChannel channel, final Member member, final long xp) {
        if (member == null) {
            LOGGER.error("Member is null. Cannot add XP.");
            return;
        }

        PlayerLevel playerLevel = playerLevelService.getPlayerLevel(member.getIdLong());
        long xpToAdd = getBoostedXp(member, xp);
        playerLevel.setXp(playerLevel.getXp() + xpToAdd);
        LOGGER.info("Added {} XP to member with ID {}", xpToAdd, member.getId());

        Optional<Level> nextLevel = levelRepository.findNextLevelByMaxXp(playerLevel.getXp());
        nextLevel.ifPresent(level -> {
            playerLevel.setLevel(level);
            playerLevelService.updatePlayerLevel(playerLevel);
            messageBroadcasterService.broadcastBasicMessageEmbed(channel, "Congratulations **" + member.getEffectiveName() + "**! You have reached level " + level.getId() + "!");
        });
    }

    public void checkAndGenerateFirstLevel() {
        if (levelRepository.count() == 0) {
            int maxXp = configService.getInt("first-level-xp");
            Level level = new Level(1, maxXp);
            levelRepository.save(level);
            LOGGER.info("First level created with max XP: {}", maxXp);
        }
    }

    private long getBoostedXp(Member member, final long xp) {
        int maxBoostValue = 1;
        for (JsonElement boost : configService.getJsonArray("boost-role-xp")) {
            String roleId = boost.getAsJsonObject().get("role-id").getAsString();
            if (member.getRoles().stream().anyMatch(role -> role.getId().equals(roleId))) {
                int boostValue = boost.getAsJsonObject().get("value").getAsInt();
                maxBoostValue = Math.max(maxBoostValue, boostValue);
            }
        }
        return xp * maxBoostValue;
    }
}
