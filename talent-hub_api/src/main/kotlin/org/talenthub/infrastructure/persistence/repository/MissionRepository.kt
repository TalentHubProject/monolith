package org.talenthub.infrastructure.persistence.repository

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.talenthub.infrastructure.persistence.entity.Mission
import java.time.LocalDateTime

@ApplicationScoped
class MissionRepository : ReactivePanacheMongoRepository<Mission> {
    fun findLastMissionCreatedAtByUser(employerSnowflake: String): Uni<LocalDateTime?> {
        return find("employerSnowflake", employerSnowflake)
            .list<Mission>()
            .onItem().transform { missions ->
                missions.maxByOrNull { it.createdAt!! }?.createdAt
            }
    }
}