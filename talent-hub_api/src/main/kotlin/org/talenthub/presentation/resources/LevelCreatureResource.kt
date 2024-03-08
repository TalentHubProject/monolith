package org.talenthub.presentation.resources

import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.Response
import org.jboss.logging.Logger
import org.talenthub.infrastructure.creature.service.CreatureFactory

@Path("/creatures")
class LevelCreatureResource @Inject constructor(
    private val _creatureFactory: CreatureFactory,
    private val _logger: Logger
) {
    @GET
    @Produces("image/png")
    fun getEntityForLevelAndRace(
        @QueryParam("level") level: Int,
        @QueryParam("raceId") raceId: Int
    ): Uni<Response> {

        return _creatureFactory.createCreature(level, raceId)
            .map { imageBytes ->
                if (imageBytes.isNotEmpty()) {
                    Response.ok(imageBytes).build()
                } else {
                    Response.status(Response.Status.NOT_FOUND).build()
                }
            }
    }
}