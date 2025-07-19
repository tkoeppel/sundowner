package de.tkoeppel.sundowner.to.auth

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Authentication request with username")
data class AuthRequestTO(
	@Schema(description = "The username of the user") val username: String,
	@Schema(description = "The password of the user") val password: String
)


