package org.alex256.application.repository

import org.alex256.application.model.ERole
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest(excludeAutoConfiguration = [EmbeddedMongoAutoConfiguration::class])
class UserRepositoryTest(@Autowired private val repository: UserRepository) {

    private val existentUsername = "alex256"
    private val nonExistentUsername = "alex255"

    @Test
    fun `find user by username test`() {
        val user = repository.findByUsername(existentUsername)
        Assertions.assertNotNull(user)
        Assertions.assertEquals(existentUsername, user?.username)
        Assertions.assertTrue(user!!.roles.stream().map { it.name }.anyMatch{ it == ERole.ADMIN })
    }

    @Test
    fun `find non existent user by username test`() {
        val user = repository.findByUsername(nonExistentUsername)
        Assertions.assertNull(user)
    }

    @Test
    fun `checks that user exists`() {
        Assertions.assertTrue(repository.existsUserByUsername(existentUsername))
    }

    @Test
    fun `checks that user not exists`() {
        Assertions.assertFalse(repository.existsUserByUsername(nonExistentUsername))
    }
}