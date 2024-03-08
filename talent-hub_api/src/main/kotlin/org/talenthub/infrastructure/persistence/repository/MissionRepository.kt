package org.talenthub.infrastructure.persistence.repository

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository
import jakarta.enterprise.context.ApplicationScoped
import org.talenthub.infrastructure.persistence.entity.Mission

@ApplicationScoped
class MissionRepository
    : ReactivePanacheMongoRepository<Mission>