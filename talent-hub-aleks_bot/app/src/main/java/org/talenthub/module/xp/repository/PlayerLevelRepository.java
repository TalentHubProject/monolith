package org.talenthub.module.xp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.talenthub.module.xp.entity.PlayerLevel;

import java.util.List;

@Repository
public interface PlayerLevelRepository extends JpaRepository<PlayerLevel, Long> {

    @Query("SELECT p FROM PlayerLevel p ORDER BY p.xp DESC LIMIT 10")
    List<PlayerLevel> findTop10ByOrderByExperienceDesc();
}
