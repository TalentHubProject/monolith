package org.talenthub.infrastructure.creature.service

import io.smallrye.mutiny.Uni
import jakarta.ws.rs.core.Response

interface CreatureFactory {
    fun createCreature(level: Int, raceId: Int): Uni<Response>
    fun getRandomRaceId(): Int
}