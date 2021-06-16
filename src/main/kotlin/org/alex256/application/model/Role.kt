package org.alex256.application.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "roles")
data class Role(
    @Id
    val id: ObjectId,
    @Field("role")
    val name: ERole
)