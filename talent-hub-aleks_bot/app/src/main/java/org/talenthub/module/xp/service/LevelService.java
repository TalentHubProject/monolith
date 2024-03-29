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
        long newXp = playerLevel.getXp() + getBoostedXp(member, xp);

        playerLevel.setXp(newXp);
        LOGGER.info("Added XP to member with ID {}. New XP: {}", member.getId(), newXp);

        Optional<Level> nextLevelOpt = levelRepository.findNextLevelByMaxXp(newXp);
        while (nextLevelOpt.isPresent() && playerLevel.getLevel().getId() < nextLevelOpt.get().getId()) {

            // Si le joueur a atteint un nouveau niveau
            if (newXp >= nextLevelOpt.get().getMaxXp()) {
                playerLevel.setLevel(nextLevelOpt.get());
                playerLevelService.updatePlayerLevel(playerLevel);
                broadcastLevelUpMessage(channel, member, nextLevelOpt.get());
                LOGGER.info("Member with ID {} leveled up to {}", member.getId(), playerLevel.getLevel().getId());
            } else {
                break;
            }

            // Sinon, continue de vérifier si le joueur a atteint un autre niveau
            newXp -= nextLevelOpt.get().getMaxXp();
            nextLevelOpt = levelRepository.findNextLevelByMaxXp(newXp);
        }
    }



    public void checkAndGenerateFirstLevel() {
        if (levelRepository.count() == 0) {
            int maxXp = configService.getInt("first-level-xp");
            Level level = new Level(1, maxXp);
            levelRepository.save(level);
            LOGGER.info("First level created with max XP: {}", maxXp);
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

