package org.talenthub.infrastructure.persistence.entity

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity
import java.time.LocalDate

data class Mission(
    var name: String,
    var description: String,
    var budget: Int,
    var deadline: String,
    var domain: String,
    var status: String,
    var employerSnowflake: String,
    var createdAt: LocalDate? = LocalDate.now(),
    var updatedAt: LocalDate? = LocalDate.now(),


    ) : ReactivePanacheMongoEntity() {
    constructor()
            : this("", "", 0, "", "", "", "", LocalDate.now(), LocalDate.now())
}