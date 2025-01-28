package de.tkoeppel.sundowner.security.tls.keystore

import de.tkoeppel.sundowner.security.tls.StoreConfig
import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "security.keystore")
data class KeyStoreConfig(
	@field:NotEmpty(message = "KeyStore type must not be empty") override val type: String,
	@field:NotEmpty(message = "KeyStore path must not be empty") override val path: String,
	@field:NotEmpty(message = "KeyStore password must not be empty") override val password: String,
	override val keys: Map<String, String>
) : StoreConfig(type, path, password, keys)