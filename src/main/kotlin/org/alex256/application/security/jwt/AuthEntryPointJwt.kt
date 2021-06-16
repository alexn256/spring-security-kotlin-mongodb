package org.alex256.application.security.jwt

import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED

@Component
class AuthEntryPointJwt: AuthenticationEntryPoint {

    val logger = LoggerFactory.getLogger(AuthEntryPointJwt::class.java)

    /**
     * Generates error 401 (Unauthorized) into HTTP response, in case request is unauthorized.
     */
    override fun commence(req: HttpServletRequest, res: HttpServletResponse, e: AuthenticationException) {
        logger.error("Unauthorized error: ${e.message}")
        res.sendError(SC_UNAUTHORIZED, "Error: Unauthorized")
    }
}