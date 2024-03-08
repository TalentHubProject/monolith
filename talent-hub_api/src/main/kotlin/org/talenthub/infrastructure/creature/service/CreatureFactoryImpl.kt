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

package org.talenthub.infrastructure.creature.service

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.jboss.logging.Logger

@ApplicationScoped
class CreatureFactoryService @Inject constructor(
    private val _logger: Logger
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

    override fun createCreature(level: Int, raceId: Int): Uni<ByteArray> {
        val imagePath = generateImagePath(level, raceId)
        return Uni.createFrom().item {
            val inputStream = javaClass.getResourceAsStream(imagePath)

            if (inputStream == null) {
                _logger.error("Image not found: $imagePath")
                ByteArray(0)
            } else {
                inputStream.readBytes()
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
        val folderName = "${type}s"
        val imageName = "${type}_$imageIndex.png"

        _logger.info("Generated image path: /META-INF/resources/$folderName/$raceName/$imageName")

        return "/META-INF/resources/$folderName/$raceName/$imageName"
    }
}