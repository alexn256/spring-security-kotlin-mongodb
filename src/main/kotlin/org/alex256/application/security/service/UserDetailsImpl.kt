package org.alex256.application.security.service

import com.fasterxml.jackson.annotation.JsonIgnore
import org.alex256.application.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
    val id: String,
    private val username: String,
    val email: String,
    @JsonIgnore
    private val password: String,
    private val roles: Set<out GrantedAuthority>
) : UserDetails {

    companion object {
        fun build(user: User): UserDetailsImpl {
            val authorities = user.roles.map {
                SimpleGrantedAuthority("ROLE_${it.name.name}")
            }.toSet()
            return UserDetailsImpl(
                user.id.toHexString(),
                user.username,
                user.email,
                user.password,
                authorities
            )
        }
    }

    override fun getAuthorities(): Collection<out GrantedAuthority> = roles

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}