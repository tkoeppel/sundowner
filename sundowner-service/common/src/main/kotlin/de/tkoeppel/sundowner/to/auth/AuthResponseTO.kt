package de.tkoeppel.sundowner.to.auth

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Authentication response with access and refresh token")
data class AuthResponseTO(
	@Schema(description = "The access token") val accessToken: String,
	@Schema(description = "The refresh token") val refreshToken: String
)