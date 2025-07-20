package de.tkoeppel.sundowner.to.auth

import io.swagger.v3.oas.annotations.media.Schema
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Schema(description = "Authentication response with access and refresh token")
data class AuthResponseTO @OptIn(ExperimentalUuidApi::class) constructor(
	@Schema(description = "The access token") val accessToken: String,
	@Schema(description = "The refresh token") val refreshToken: Uuid
)