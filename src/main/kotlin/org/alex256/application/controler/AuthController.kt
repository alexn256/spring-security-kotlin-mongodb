package org.alex256.application.controler

import org.alex256.application.model.ERole
import org.alex256.application.model.JwtResponse
import org.alex256.application.model.LoginRequest
import org.alex256.application.model.Role
import org.alex256.application.model.SignupRequest
import org.alex256.application.model.User
import org.alex256.application.repository.RoleRepository
import org.alex256.application.repository.UserRepository
import org.alex256.application.security.JwtUtils
import org.alex256.application.security.service.UserDetailsImpl
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils
) {

    @PostMapping("/signup")
    fun register(@Valid @RequestBody req: SignupRequest): ResponseEntity<*> {
        if (userRepository.existsUserByUsername(req.username)) {
            return ResponseEntity.badRequest().body("Error: Username is already used!")
        }
        if (userRepository.existsUserByEmail(req.email)) {
            return ResponseEntity.badRequest().body("Error: Email is already in used!")
        }
        val user = User(req.username, req.email, passEncoder.encode(req.password))
        val roles: Set<Role> = if (req.roles == null) {
            val role = roleRepository.findByName(ERole.USER) ?: throw RuntimeException("Error: Role is not found!")
            setOf(role)
        } else {
            req.roles.map {
                when (it) {
                    "admin" -> {
                        roleRepository.findByName(ERole.ADMIN)
                    }
                    else -> {
                        roleRepository.findByName(ERole.USER)
                    }
                } ?: throw RuntimeException("Error: Role is not found!")
            }.toSet()
        }
        user.roles = roles
        userRepository.save(user)
        return ResponseEntity.ok("User registered successfully!")
    }

    @PostMapping("/signin")
    fun login(@Valid @RequestBody req: LoginRequest): ResponseEntity<*> {
        val authentication = authManager.authenticate(UsernamePasswordAuthenticationToken(req.username, req.password))
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)
        val userDetails = authentication.principal as UserDetailsImpl
        val roles = userDetails.authorities.map { it.authority }.toList()
        return ResponseEntity.ok(
            JwtResponse(
                jwt,
                userDetails.id,
                userDetails.username,
                userDetails.email,
                roles
            )
        )
    }
}