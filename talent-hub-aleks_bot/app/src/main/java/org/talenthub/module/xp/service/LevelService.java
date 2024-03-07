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
import org.talenthub.module.xp.task.ActivityCalculTask;
import org.talenthub.service.ConfigService;
import org.talenthub.service.MessageBroadcasterService;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LevelService {

    private final PlayerLevelService playerLevelService;
    private final LevelRepository levelRepository;
    private final MessageBroadcasterService messageBroadcasterService;
    private final ConfigService configService;
    private final Logger LOGGER = LoggerFactory.getLogger(LevelService.class);

    @Transactional
    public void addXp(final GuildMessageChannel channel, final Member member, final long xp){

        PlayerLevel playerLevel = playerLevelService.getPlayerLevel(member.getIdLong());

        //long xpToAdd = ActivityCalculTask.getInstance().isBoostActivated() ? xp * 2 : xp;
        //xpToAdd = getBoostedXp(member, xpToAdd);
        long xpToAdd = xp;
        xpToAdd = getBoostedXp(member, xpToAdd);

        playerLevel.setXp(playerLevel.getXp() + xpToAdd);
        LOGGER.info("Xp add : " + xpToAdd + " to "+member.getId());

        if (playerLevel.getLevel().getMaxXp() <= playerLevel.getXp()) {
            new Thread(() -> {
                try {
                    Level newLevel = levelUp(playerLevel);
                    messageBroadcasterService.broadcastBasicMessageEmbed(channel, "Félécitation **" + member.getEffectiveName() + "**! Tu as atteint le niveau " + newLevel.getId() + "!");
                } catch (Exception e) {
                    LOGGER.error("Error while leveling up player: " + member.getId(), e);
                }
            }).start();
        } else {
            playerLevelService.updatePlayerLevel(playerLevel);
        }

    }

    @Transactional
    public Level levelUp(final PlayerLevel playerLevel){

        Level newlevel = levelRepository.findNextLevelByMaxXp(playerLevel.getXp()).orElseGet(() -> createNewLevels(playerLevel.getXp(), playerLevel.getLevel()));
        playerLevel.setLevel(newlevel);

        playerLevelService.updatePlayerLevel(playerLevel);

        return newlevel;
    }

    public void checkAndGenerateFirsLevel() {

        if (levelRepository.count() == 0) {

            int maxXp = configService.getInt("first-level-xp");
            Level level = new Level(1, maxXp);

            levelRepository.save(level);

        }
    }

    private Level createNewLevels(long targetXp, Level level) {
        long currentXp = level.getMaxXp();
        int currentLevel = level.getId();

        List<Level> newLevels = new ArrayList<>();

        while (currentXp < targetXp) {
            currentLevel++;
            currentXp *= (long) 1.2;
            Level newLevel = new Level(currentLevel, currentXp);
            newLevels.add(newLevel);
        }

        levelRepository.saveAll(newLevels);

        return newLevels.get(newLevels.size() - 1);
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
