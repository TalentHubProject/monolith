package org.talenthub.module.xp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.talenthub.module.xp.persistence.Level;
import org.talenthub.module.xp.persistence.PlayerLevel;
import org.talenthub.module.xp.repository.LevelRepository;
import org.talenthub.module.xp.repository.PlayerLevelRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PlayerLevelService {

    private final PlayerLevelRepository playerLevelRepository;
    private final LevelRepository levelRepository;

    public PlayerLevel getPlayerLevel(final long discordId){
        return playerLevelRepository.findById(discordId).orElse(createPlayerLevel(discordId));
    }

    private PlayerLevel createPlayerLevel(final long discordId){

        Optional<Level> firstLevel = levelRepository.findById(1);

        if(firstLevel.isEmpty()){
            throw new IllegalStateException("No levels found");
        }

        PlayerLevel playerLevel = new PlayerLevel(discordId, firstLevel.get());
        playerLevelRepository.save(playerLevel);
        return playerLevel;
    }

    public void updatePlayerLevel(final PlayerLevel playerLevel){
        playerLevelRepository.save(playerLevel);
    }
}
