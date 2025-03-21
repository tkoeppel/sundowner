package de.tkoeppel.sundowner.module.storage

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.boot.context.properties.bind.DefaultValue


@ConfigurationProperties(prefix = "storage.minio")
data class StorageConfig @ConstructorBinding constructor(
	@DefaultValue("") val endpoint: String,
	@DefaultValue("") val accessKey: String,
	@DefaultValue("") val secretKey: String,
	@DefaultValue("") val trustStoreAlias: String
)