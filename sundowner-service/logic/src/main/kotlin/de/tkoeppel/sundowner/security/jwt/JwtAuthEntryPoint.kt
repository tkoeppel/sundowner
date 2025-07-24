package de.tkoeppel.sundowner.security.jwt

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthEntryPoint : AuthenticationEntryPoint {

	override fun commence(
		request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException?
	) {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
		response.writer.write("{\"error\": \"Unauthorized\", \"message\": \"${authException?.message ?: "Authentication required"}\"}")
	}
}