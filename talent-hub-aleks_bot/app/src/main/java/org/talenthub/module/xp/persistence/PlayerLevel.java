package org.talenthub.module.xp.persistence;

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

    public PlayerLevel(long discordId, Level level){
        this.discordId = discordId;
        this.level = level;
        this.xp = 0;
    }
}
