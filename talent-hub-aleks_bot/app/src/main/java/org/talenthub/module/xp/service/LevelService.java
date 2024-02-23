package org.talenthub.module.xp.service;

import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import org.springframework.stereotype.Service;
import org.talenthub.module.xp.entity.Level;
import org.talenthub.module.xp.entity.PlayerLevel;
import org.talenthub.module.xp.repository.LevelRepository;
import org.talenthub.module.xp.task.ActivityCalculTask;
import org.talenthub.service.ConfigService;
import org.talenthub.service.MessageBroadcasterService;

@Service
@AllArgsConstructor
public class LevelService {

    private final PlayerLevelService playerLevelService;
    private final LevelRepository levelRepository;
    private final MessageBroadcasterService messageBroadcasterService;
    private final ConfigService configService;

    public void addXp(final GuildMessageChannel channel, final Member member, final long xp){

        PlayerLevel playerLevel = playerLevelService.getPlayerLevel(member.getIdLong());

        long xpToAdd = ActivityCalculTask.getInstance().isBoostActivated() ? xp * 2 : xp;
        xpToAdd = getBoostedXp(member, xpToAdd);

        playerLevel.setXp(playerLevel.getXp() + xpToAdd);

        if(playerLevel.getLevel().getMaxXp() <= playerLevel.getXp()){

            Level newLevel = levelUp(playerLevel);

            messageBroadcasterService.broadcastBasicMessageEmbed(channel, "Félécitation **" + member.getEffectiveName() + "**! Tu as atteint le niveau " + newLevel.getId() + "!");

        }else{
            playerLevelService.updatePlayerLevel(playerLevel);
        }

    }

    private Level levelUp(final PlayerLevel playerLevel){

        Level newlevel = levelRepository.findNextLevelByMaxXp(playerLevel.getXp()).orElse(createNewlevel(playerLevel.getLevel()));
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

    private Level createNewlevel(Level level){
        Level newLevel = new Level(level.getId() + 1, (long) (level.getMaxXp() * 1.2));
        levelRepository.save(newLevel);
        return newLevel;
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
