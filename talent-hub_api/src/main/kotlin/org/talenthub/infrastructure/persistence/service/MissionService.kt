package org.talenthub.infrastructure.persistence.service

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.talenthub.infrastructure.persistence.entity.Mission
import org.talenthub.infrastructure.persistence.repository.MissionRepository
import java.time.Duration
import java.time.LocalDateTime

@ApplicationScoped
class MissionService @Inject constructor(
    private val _missionRepository: MissionRepository
) {
    fun createMission(mission: Mission): Uni<Result<Mission>> {
        return _missionRepository.findLastMissionCreatedAtByUser(mission.employerSnowflake)
            .onItem().transformToUni { lastMissionCreatedAt ->
                if (lastMissionCreatedAt != null && Duration.between(
                        lastMissionCreatedAt,
                        LocalDateTime.now()
                    ) < Duration.ofHours(3)
                ) {
                    Uni.createFrom().item(Result.Error("Please wait before creating a new mission."))
                } else {
                    _missionRepository.persist(mission)
                        .onItem().transform { createdMission -> Result.Success(createdMission) as Result<Mission> }
                        .onFailure().recoverWithItem { throwable ->
                            Result.Error(throwable.message ?: "Failed to create mission.")
                        }
                }
            }
    }
}

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}