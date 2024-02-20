package org.talenthub.module.xp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@Getter
public class PlayerLevel {

    @Id
    private long discordId;

    @ManyToOne
    @Setter
    private Level level;

    @Column
    @Setter
    private long xp;

    @Column
    private int raceId;

    public PlayerLevel(long discordId, Level level, final int raceId){
        this.discordId = discordId;
        this.level = level;
        this.xp = 0;
        this.raceId = raceId;
    }
}
