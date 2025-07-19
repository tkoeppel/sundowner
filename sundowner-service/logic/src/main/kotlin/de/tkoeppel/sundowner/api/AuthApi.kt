package de.tkoeppel.sundowner.api

import de.tkoeppel.sundowner.to.auth.AuthRequestTO
import de.tkoeppel.sundowner.to.auth.AuthResponseTO
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Tag(
	name = "Auth", description = "API handling all authentication requests."
)
@Validated
@RequestMapping("/api/v1/auth")
interface AuthApi {

	// TODO Create rate limit per IP
	@PostMapping("/login")
	fun login(
		@RequestBody authRequest: AuthRequestTO
	): AuthResponseTO

	@PostMapping("/logout")
	fun logout(
		@RequestBody refreshToken: String
	)

	@PostMapping("/refresh")
	fun refresh(
		@RequestBody refreshToken: String
	): String

	@PostMapping("/signup")
	fun signup(
		@RequestBody authRequest: AuthRequestTO
	)


}