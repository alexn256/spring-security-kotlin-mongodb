package org.alex256.application.security

import org.alex256.application.model.ERole
import org.alex256.application.model.Role
import org.alex256.application.model.User
import org.alex256.application.security.service.UserDetailsImpl
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

@SpringBootTest
internal class JwtUtilsTest(@Autowired val utils: JwtUtils) {

    @Test
    fun `checks jwt constants injection`() {
        val jwtSecret = utils.jwtSecret
        val jwtExpirationMs = utils.jwtExpirationMs

        Assertions.assertTrue(jwtSecret != null && !jwtSecret.isBlank())
        Assertions.assertTrue(jwtExpirationMs != null && jwtExpirationMs != 0)
    }

    @Test
    fun `extract username from jwt token test`() {
        val expected = "moderator"
        val user = User(ObjectId(), expected,"email@email.com", "123456789", setOf(Role(ObjectId(), ERole.USER)))
        val userDetails = UserDetailsImpl.build(user)
        val token = utils.generateJwtToken(UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities))
        val actual = utils.getUserNameFromJwtToken(token)

        Assertions.assertNotNull(actual)
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `generate and validate token test`() {
        val user = User(ObjectId(), "moderator","email@email.com", "123456789", setOf(Role(ObjectId(), ERole.USER)))
        val userDetails = UserDetailsImpl.build(user)
        val token = utils.generateJwtToken(UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities))

        Assertions.assertNotNull(token)
        Assertions.assertTrue(!token.isEmpty() && !token.isBlank())
        Assertions.assertTrue(utils.validateJwtToken(token))
    }
}