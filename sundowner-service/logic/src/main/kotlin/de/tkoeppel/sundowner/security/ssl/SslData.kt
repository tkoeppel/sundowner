package de.tkoeppel.sundowner.security.ssl

import jakarta.validation.constraints.NotEmpty
import org.springframework.validation.annotation.Validated

@Validated
data class SslData(
	@field:NotEmpty(message = "It should be clear if SSL will be used") val enabled: Boolean = false,
	@field:NotEmpty(message = "KeyStore type must not be empty") val keystoreType: String,
	@field:NotEmpty(message = "KeyStore path must not be empty") val keystorePath: String,
	@field:NotEmpty(message = "KeyStore password must not be empty") val keystorePassword: String,
	@field:NotEmpty(message = "Key password must not be empty") val keyPassword: String?
)