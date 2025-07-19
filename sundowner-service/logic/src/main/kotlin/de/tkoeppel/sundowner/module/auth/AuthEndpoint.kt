package de.tkoeppel.sundowner.module.auth

import de.tkoeppel.sundowner.api.AuthApi
import de.tkoeppel.sundowner.to.auth.AuthRequestTO
import de.tkoeppel.sundowner.to.auth.AuthResponseTO
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthEndpoint(private val authService: AuthService) : AuthApi {
	/** @see AuthApi.login */
	override fun login(authRequest: AuthRequestTO): AuthResponseTO {
		val authResponse = this.authService.login(authRequest)
		return authResponse
	}

	/** @see AuthApi.logout */
	override fun logout(refreshToken: String) {
		this.authService.logout(refreshToken)
	}

	/** @see AuthApi.refresh */
	override fun refresh(refreshToken: String): String {
		val accessToken = this.authService.refreshAccessToken(refreshToken)
		return accessToken
	}

	/** @see AuthApi.signup */
	override fun signup(authRequest: AuthRequestTO) {
		TODO("Not yet implemented")
	}
}