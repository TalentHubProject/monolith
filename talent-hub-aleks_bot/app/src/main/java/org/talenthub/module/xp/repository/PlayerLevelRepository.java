package org.talenthub.module.xp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.talenthub.module.xp.entity.PlayerLevel;

@Repository
public interface PlayerLevelRepository extends JpaRepository<PlayerLevel, Long> {
}
