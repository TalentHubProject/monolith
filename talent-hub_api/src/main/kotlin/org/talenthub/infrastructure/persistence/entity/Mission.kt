package org.talenthub.infrastructure.persistence.entity

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity
import java.time.LocalDateTime

data class Mission(
    var name: String,
    var description: String,
    var budget: Int,
    var deadline: String,
    var domain: String,
    var status: String,
    var employerSnowflake: String,
    var employerName: String,
    var createdAt: LocalDateTime? = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = LocalDateTime.now(),


    ) : ReactivePanacheMongoEntity() {
    constructor()
            : this("", "", 0, "", "", "", "", "", LocalDateTime.now(), LocalDateTime.now())
}