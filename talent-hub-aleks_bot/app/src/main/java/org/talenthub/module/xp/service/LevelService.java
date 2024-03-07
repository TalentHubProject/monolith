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
        PlayerLevel playerLevel = playerLevelService.getPlayerLevel(member.getIdLong());

        long xpToAdd = getBoostedXp(member, xp);
        playerLevel.setXp(playerLevel.getXp() + xpToAdd);
        LOGGER.info("Xp add : " + xpToAdd + " to " + member.getId());

        Optional<Level> nextLevel = levelRepository.findNextLevelByMaxXp(playerLevel.getXp());
        if (nextLevel.isPresent()) {
            playerLevel.setLevel(nextLevel.get());
            playerLevelService.updatePlayerLevel(playerLevel);
            messageBroadcasterService.broadcastBasicMessageEmbed(channel, "Félicitations **" + member.getEffectiveName() + "**! Tu as atteint le niveau " + nextLevel.get().getId() + "!");
        } else {
            playerLevelService.updatePlayerLevel(playerLevel);
        }
    }

    public void checkAndGenerateFirstLevel() {
        if (levelRepository.count() == 0) {
            int maxXp = configService.getInt("first-level-xp");
            Level level = new Level(1, maxXp);
            levelRepository.save(level);
        }
    }

    private long getBoostedXp(Member member, final long xp) {
        int maxBoostValue = 1;

        for (JsonElement boost : configService.getJsonArray("boost-role-xp")) {
            String roleId = boost.getAsJsonObject().get("role-id").getAsString();

            if (member.getRoles().stream().anyMatch(role -> role.getId().equals(roleId))) {
                int boostValue = boost.getAsJsonObject().get("value").getAsInt();
                if (boostValue > maxBoostValue) {
                    maxBoostValue = boostValue;
                }
            }
        }

        return xp * maxBoostValue;
    }
}