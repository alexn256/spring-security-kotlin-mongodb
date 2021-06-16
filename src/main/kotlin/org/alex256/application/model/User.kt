package org.alex256.application.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.Email

@Document(collection = "users")
data class User(
    val username: String,
    @Email
    val email: String,
    val password: String
) {
    @Id
    lateinit var id: ObjectId
    @DBRef
    lateinit var roles: Set<Role>

    constructor(
        id: ObjectId,
        username: String,
        email: String,
        password: String,
        roles: Set<Role>
    ) : this(username, email, password) {
        this.id = id
        this.roles = roles
    }
}
