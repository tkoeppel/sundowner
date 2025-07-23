package de.tkoeppel.sundowner.module.auth

import de.tkoeppel.sundowner.api.AuthApi
import de.tkoeppel.sundowner.to.auth.AuthRequestTO
import de.tkoeppel.sundowner.to.auth.AuthResponseTO
import org.springframework.web.bind.annotation.RestController
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@RestController
class AuthEndpoint(private val authService: AuthService) : AuthApi {
	/** @see AuthApi.login */
	override fun login(authRequest: AuthRequestTO): AuthResponseTO {
		val authResponse = this.authService.login(authRequest)
		return authResponse
	}

	/** @see AuthApi.logout */
	override fun logout() {
		this.authService.logout()
	}

	/** @see AuthApi.refresh */
	@OptIn(ExperimentalUuidApi::class)
	override fun refresh(refreshToken: Uuid): String {
		val accessToken = this.authService.refreshAccessToken(refreshToken)
		return accessToken
	}

	/** @see AuthApi.signup */
	override fun signup(authRequest: AuthRequestTO) {
		TODO("Not yet implemented")
	}
}