package de.tkoeppel.sundowner.module.auth

import de.tkoeppel.sundowner.module.users.SundownerUserDetailsService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class SundownerAuthProvider(
	private val userDetailsService: SundownerUserDetailsService, private val passwordEncoder: PasswordEncoder
) : AuthenticationProvider {

	override fun authenticate(authentication: Authentication): Authentication {
		val username = authentication.name
		val password = authentication.credentials.toString()

		val user = userDetailsService.loadUserByUsername(username)

		if (!passwordEncoder.matches(password, user.password)) {
			throw BadCredentialsException("Invalid password")
		}

		return UsernamePasswordAuthenticationToken(user, password, user.authorities)
	}

	override fun supports(authentication: Class<*>): Boolean {
		return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
	}
}