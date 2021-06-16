package org.alex256.application.model

data class JwtResponse(
    val token: String,
    val id: String,
    val username: String,
    val email: String,
    val roles: List<String>
) {
    val type:String = "Bearer"
}