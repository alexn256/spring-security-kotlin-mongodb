package org.alex256.application.repository

import org.alex256.application.model.ERole
import org.alex256.application.model.Role
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository: MongoRepository<Role, String> {
    fun findByName(name: ERole): Role?
}