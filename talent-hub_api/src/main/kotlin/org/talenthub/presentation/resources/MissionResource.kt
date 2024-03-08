package org.talenthub.presentation.resources

import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.bson.types.ObjectId
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.jboss.logging.Logger
import org.talenthub.infrastructure.persistence.entity.Mission
import org.talenthub.infrastructure.persistence.repository.MissionRepository
import java.net.http.HttpClient

@Path("/missions")
class MissionResource @Inject constructor(
    private val _missionRepository: MissionRepository,
    private val _logger: Logger
) {

    @ConfigProperty(name = "discord.jobs.webhook")
    lateinit var webhookUrl: String

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllMissions(): Uni<Response> {
        return _missionRepository.listAll()
            .map { missions ->
                Response.ok(missions).build()
            }
            .onFailure().recoverWithItem { throwable ->
                _logger.error("Failed to retrieve missions", throwable)
                Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
            }
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getMissionById(id: String): Uni<Response> {
        return try {
            val objectId = ObjectId(id)

            _missionRepository.findById(objectId)
                .map { mission ->
                    if (mission != null) {
                        Response.ok(mission).build()
                    } else {
                        Response.status(Response.Status.NOT_FOUND).build()
                    }
                }
                .onFailure().recoverWithItem(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build())

        } catch (e: IllegalArgumentException) {
            Uni.createFrom().item(Response.status(Response.Status.BAD_REQUEST).build())
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createMission(mission: Mission): Uni<Response> {
        return _missionRepository.persist(mission)
            .map { createdMission ->

                _logger.info("Webhook : $webhookUrl")

                val payload = """
                    {
                        "content": "New mission created",
                        "embeds": [
                            {
                                "title": "New mission created",
                                "description": "A new mission has been created with the following details: ${createdMission.name}, ${createdMission.description}"
                            }
                        ]
                    }
                """.trimIndent()

                try {
                    HttpClient.newHttpClient()
                        .sendAsync(
                            java.net.http.HttpRequest.newBuilder()
                                .uri(java.net.URI.create(webhookUrl))
                                .header("Content-Type", "application/json")
                                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(payload))
                                .build(),
                            java.net.http.HttpResponse.BodyHandlers.ofString()
                        )
                        .join()

                } catch (e: Exception) {
                    _logger.error("Failed to send webhook", e)
                }

                Response.status(Response.Status.CREATED)
                    .entity(createdMission)
                    .build()
            }
            .onFailure().recoverWithItem(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build())
    }
}