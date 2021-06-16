package org.alex256.application.security.jwt

import org.alex256.application.security.JwtUtils
import org.alex256.application.security.service.MongoUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory

class AuthTokenFilter : OncePerRequestFilter() {

    private val logger = LoggerFactory.getLogger(AuthTokenFilter::class.java)

    @Autowired
    private lateinit var jwtUtils: JwtUtils

    @Autowired
    private lateinit var userDetailsService: MongoUserDetailsService

    /**
     * Creates the [Authentication] object from HTTP request and
     * set current [UserDetails] in [SecurityContext]
     */
    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, filterChain: FilterChain) {
        val jwt = parseJwt(req)
        try {
            jwt?.let {
                if (jwtUtils.validateJwtToken(jwt)) {
                    val username = jwtUtils.getUserNameFromJwtToken(jwt)
                    val userDetails = userDetailsService.loadUserByUsername(username)
                    val authentication = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails?.authorities
                    )
                    authentication.details = WebAuthenticationDetailsSource().buildDetails(req)
                    SecurityContextHolder.getContext().authentication = authentication

                }
            }
        } catch (e: Exception) {
            logger.error("Cannot set user authentication: $e")
        }
        filterChain.doFilter(req, res)
    }

    /**
     * Extracts authorization header from HTTP request,
     * and then extracts from t JWT token (hash only).
     */
    private fun parseJwt(req: HttpServletRequest): String? {
        val header = req.getHeader("Authorization")
        return header?.let {
            if (header.isNotBlank() && header.startsWith("Bearer ")) {
                header.substring(7, header.length)
            } else {
                null
            }
        }
    }
}