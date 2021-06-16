package org.alex256.application.security.service

import org.alex256.application.model.ERole
import org.alex256.application.model.Role
import org.alex256.application.model.User
import org.alex256.application.repository.UserRepository
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.core.userdetails.UsernameNotFoundException

@ExtendWith(MockitoExtension::class)
class MongoUserDetailServiceTest {

    @Spy
    lateinit var userRepository: UserRepository
    @InjectMocks
    lateinit var userDetailService: MongoUserDetailsService

    @Test
    fun `if User with specified username are not exist` () {
        val username = "NonExistent"
        Mockito.`when`(userRepository.findByUsername(username)).thenReturn(null)
        val exception = Assertions.assertThrows(UsernameNotFoundException::class.java
        ) {
            userDetailService.loadUserByUsername(username)
        }
        Assertions.assertEquals("User Not Found with username: $username", exception.message)
    }

    @Test
    fun `if User with specified username are exist` () {
        val username = "username"
        val user = User(
            ObjectId(),
            username,
            "username@email.com",
            "password",
            setOf(
                Role(ObjectId(), ERole.ADMIN),
                Role(ObjectId(), ERole.USER)
            )
        )

        Mockito.`when` (userRepository.findByUsername(username)).thenReturn(user)
        val userDetails = userDetailService.loadUserByUsername(username)

        Assertions.assertNotNull(userDetails)
        Assertions.assertEquals(user.username, userDetails?.username)
        Assertions.assertEquals(user.password, userDetails?.password)
        Assertions.assertEquals(user.roles.size, userDetails?.authorities?.size)
    }
}