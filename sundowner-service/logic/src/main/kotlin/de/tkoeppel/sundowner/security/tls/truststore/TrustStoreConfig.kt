package de.tkoeppel.sundowner.security.tls.truststore

import de.tkoeppel.sundowner.security.tls.StoreConfig
import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "security.truststore")
data class TrustStoreConfig @ConstructorBinding constructor(
	@field:NotEmpty(message = "KeyStore type must not be empty") override val type: String,
	@field:NotEmpty(message = "KeyStore path must not be empty") override val path: String,
	@field:NotEmpty(message = "KeyStore password must not be empty") override val password: String,
	override val keys: Map<String, String>
) : StoreConfig(type, path, password, keys)