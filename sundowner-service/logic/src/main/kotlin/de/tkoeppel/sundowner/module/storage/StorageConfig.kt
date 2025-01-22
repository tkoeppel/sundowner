package de.tkoeppel.sundowner.module.storage

import io.minio.MinioClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@ConfigurationProperties("storage.minio")
class StorageConfig {
	lateinit var endpoint: String
	lateinit var accessKey: String
	lateinit var secretKey: String

	@Bean
	fun minioClient(): MinioClient {
		return MinioClient.builder() //
			.endpoint(endpoint) //
			.credentials(accessKey, secretKey) //
			.build()
	}
}