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

        boolean leveledUp = false;
        while (true) {
            Optional<Level> nextLevel = levelRepository.findNextLevelByMaxXp(playerLevel.getXp());
            if (nextLevel.isPresent() && playerLevel.getLevel().getId() < nextLevel.get().getId()) {
                leveledUp = true;
                playerLevel.setLevel(nextLevel.get());
                playerLevelService.updatePlayerLevel(playerLevel);
                LOGGER.info("Member with ID {} leveled up to {}", member.getId(), playerLevel.getLevel().getId());
            } else {
                break;
            }
        }

        if (leveledUp) {
            broadcastLevelUpMessage(channel, member, playerLevel.getLevel());
        }

        if (!leveledUp) {
            playerLevelService.updatePlayerLevel(playerLevel);
        }
    }

    private void broadcastLevelUpMessage(GuildMessageChannel channel, Member member, Level level) {
        messageBroadcasterService.broadcastBasicMessageEmbed(channel,
                String.format("Congratulations **%s**! You have reached level %d!", member.getEffectiveName(), level.getId()));
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

