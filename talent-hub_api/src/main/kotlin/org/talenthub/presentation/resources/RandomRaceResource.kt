package org.talenthub.presentation.resources

import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.talenthub.infrastructure.creature.service.CreatureFactory


@Path("/randomrace")
class RandomRaceResource @Inject constructor(
    private val _creatureFactory: CreatureFactory
) {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getRandomRaceId(): Uni<Response> {
        val raceId = _creatureFactory.getRandomRaceId()

        return Uni.createFrom().item { Response.ok(raceId).build() }
    }
}