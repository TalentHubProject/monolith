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
        long newXp = calculateNewXp(playerLevel, xp, member);

        updatePlayerXp(playerLevel, newXp);

        handleLevelUp(channel, member, playerLevel);
    }

    private long calculateNewXp(PlayerLevel playerLevel, long xp, Member member) {
        long xpToAdd = getBoostedXp(member, xp);
        LOGGER.info("Adding {} XP to member with ID {}", xpToAdd, member.getId());
        return playerLevel.getXp() + xpToAdd;
    }

    private void updatePlayerXp(PlayerLevel playerLevel, long newXp) {
        playerLevel.setXp(newXp);
        playerLevelService.updatePlayerLevel(playerLevel);
    }

    private void handleLevelUp(GuildMessageChannel channel, Member member, PlayerLevel playerLevel) {
        Optional<Level> nextLevelOpt = levelRepository.findNextLevelByMaxXp(playerLevel.getXp());
        nextLevelOpt.ifPresent(nextLevel -> {
            if (playerLevel.getLevel().getId() < nextLevel.getId()) {
                playerLevel.setLevel(nextLevel);
                playerLevelService.updatePlayerLevel(playerLevel);
                broadcastLevelUpMessage(channel, member, nextLevel);
            }
        });
    }

    private void broadcastLevelUpMessage(GuildMessageChannel channel, Member member, Level nextLevel) {
        messageBroadcasterService.broadcastBasicMessageEmbed(channel, String.format("Congratulations **%s**! You have reached level %d!", member.getEffectiveName(), nextLevel.getId()));
        LOGGER.info("Member with ID {} has leveled up to {}", member.getId(), nextLevel.getId());
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
