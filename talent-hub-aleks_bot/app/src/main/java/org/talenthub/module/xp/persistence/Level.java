package org.talenthub.module.xp.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Level {

    @Id
    private int id;

    @Column(name = "max_xp")
    private long maxXp;

    public Level(final int id, final long maxXp) {
        this.id = id;
        this.maxXp = maxXp;
    }
}
