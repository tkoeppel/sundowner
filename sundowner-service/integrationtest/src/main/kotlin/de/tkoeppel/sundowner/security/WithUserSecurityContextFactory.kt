package de.tkoeppel.sundowner.security

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.test.context.support.WithSecurityContextFactory
import java.time.Instant

class WithUserSecurityContextFactory : WithSecurityContextFactory<WithUser> {

	override fun createSecurityContext(annotation: WithUser): SecurityContext {
		val context = SecurityContextHolder.createEmptyContext()
		val roles = mutableListOf("ROLE_${annotation.username.uppercase()}")
		if (annotation.username == "admin") {
			roles.add("ROLE_USER")
		}
		val authorities = roles.map { SimpleGrantedAuthority(it) }
		val claims = mapOf(
			"sub" to annotation.username, "roles" to roles
		)

		val jwt =
			Jwt.withTokenValue("mock-token").header("alg", "none").claims { it.putAll(claims) }.issuedAt(Instant.now())
				.expiresAt(Instant.now().plusSeconds(3600)).build()

		val authentication = JwtAuthenticationToken(jwt, authorities)
		context.authentication = authentication
		return context
	}

}