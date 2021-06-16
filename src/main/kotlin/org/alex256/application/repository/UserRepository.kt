package org.alex256.application.repository

import org.alex256.application.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, String> {
    fun findByUsername(username: String): User?
    fun existsUserByUsername(username: String):Boolean
    fun existsUserByEmail(email: String): Boolean
}