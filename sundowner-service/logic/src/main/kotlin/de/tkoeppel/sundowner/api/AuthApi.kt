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

	@PostMapping
	fun authenticate(
		@RequestBody authRequest: AuthRequestTO
	): AuthResponseTO
	
}