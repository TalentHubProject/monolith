package org.talenthub.infrastructure.creature.service

import io.smallrye.mutiny.Uni

interface CreatureFactory {
    fun createCreature(level: Int, raceId: Int): Uni<ByteArray>
    fun getRandomRaceId(): Int
}