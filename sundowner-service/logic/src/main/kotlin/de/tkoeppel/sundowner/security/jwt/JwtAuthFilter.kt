package de.tkoeppel.sundowner.security.jwt

import de.tkoeppel.sundowner.module.users.SundownerUser
import de.tkoeppel.sundowner.module.users.SundownerUserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
	private val userDetailsService: SundownerUserDetailsService, //
	private val jwtService: JwtService
) : OncePerRequestFilter() {
	override fun doFilterInternal(
		request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
	) {
		val authorizationHeader: String? = request.getHeader("Authorization")

		if (null != authorizationHeader && authorizationHeader.startsWith("Bearer ")) {
			try {
				val token: String = authorizationHeader.substringAfter("Bearer ")
				val username: String = this.jwtService.extractUsername(token)

				if (SecurityContextHolder.getContext().authentication == null) {
					val userDetails: SundownerUser = userDetailsService.loadUserByUsername(username)

					if (username == userDetails.username) {
						val authToken = UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.authorities
						)
						authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
						SecurityContextHolder.getContext().authentication = authToken
					}
				}
			} catch (ex: Exception) {
				response.writer.write(
					"""{"error": "Filter Authorization error: 
                    |${ex.message ?: "unknown error"}"}""".trimMargin()
				)
			}
		}

		filterChain.doFilter(request, response)
	}
}