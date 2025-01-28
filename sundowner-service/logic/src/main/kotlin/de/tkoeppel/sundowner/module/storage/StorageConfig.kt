package de.tkoeppel.sundowner.module.storage

import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding


@ConfigurationProperties(prefix = "storage.minio")
data class StorageConfig @ConstructorBinding constructor(
	@field:NotEmpty(message = "Endpoint must not be empty") val endpoint: String,
	@field:NotEmpty(message = "Access key must not be empty") val accessKey: String,
	@field:NotEmpty(message = "Secret key must not be empty") val secretKey: String,
	val trustStoreAlias: String?
)