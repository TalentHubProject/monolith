// Copyright (c) 2023 Talent Hub
//
// Licensed under the GPL-3.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at the root directory of this repository.
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.talenthub.myfabulouscreatures.infrastructure

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.core.Response
import org.jboss.logging.Logger
import org.talenthub.infrastructure.creature.service.CreatureFactory
import java.io.File
import java.nio.file.Files
import java.util.*

@ApplicationScoped
class CreatureFactoryService @Inject constructor(
    private val logger: Logger
) : CreatureFactory {

    companion object {
        private val RACE_ID_TO_NAME = mapOf(
            1 to "Cat",
            2 to "Cow",
            3 to "Mouse",
            4 to "Snake"
        )

        private const val MAX_EGG_INDEX = 3
        private const val MIN_CREATURE_INDEX = 1
        private const val MAX_CREATURE_INDEX = 3
    }

    override fun createCreature(level: Int, raceId: Int): Uni<Response> {
        val imagePath = generateImagePath(level, raceId)
        return Uni.createFrom().item {
            val imageFile = File(imagePath)
            if (imageFile.exists()) {
                val imageBytes = Files.readAllBytes(imageFile.toPath())
                Response.ok(imageBytes, "image/png").build()
            } else {
                logger.warn("Image not found for level $level and raceId $raceId.")
                Response.status(Response.Status.NOT_FOUND).build()
            }
        }
    }

    override fun getRandomRaceId(): Int {
        return (1..RACE_ID_TO_NAME.size).random()
    }

    private fun generateImagePath(level: Int, raceId: Int): String {
        var imageIndex = level / 5
        val type = if (imageIndex <= MAX_EGG_INDEX) "egg" else "creature"

        if (type == "creature") {
            imageIndex -= MAX_EGG_INDEX
            imageIndex = imageIndex.coerceIn(MIN_CREATURE_INDEX, MAX_CREATURE_INDEX)
        }

        val raceName = RACE_ID_TO_NAME[raceId] ?: throw IllegalArgumentException("Invalid raceId")
        val imageName = "${type}_$imageIndex.png"

        return "/creatures/$raceName/$imageName"
    }
}