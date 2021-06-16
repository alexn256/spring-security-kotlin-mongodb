package org.alex256.application.repository

import org.alex256.application.model.ERole
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest(excludeAutoConfiguration = [EmbeddedMongoAutoConfiguration::class])
class RoleRepositoryTest(@Autowired private val repository: RoleRepository) {

    @Test
    fun `find user by username test`() {
        val name = ERole.ADMIN;
        val admin = repository.findByName(name)
        Assertions.assertNotNull(admin)
        Assertions.assertNotNull(admin?.id)
        Assertions.assertEquals(name, admin?.name)
    }
}