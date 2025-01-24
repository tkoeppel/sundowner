package de.tkoeppel.sundowner.module.storage

import de.tkoeppel.sundowner.security.ssl.KeyStoreLoader
import de.tkoeppel.sundowner.security.ssl.SslData
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
	lateinit var ssl: SslData


	@Bean
	fun minioClient(): MinioClient {
		val builder = MinioClient.builder() //
			.endpoint(endpoint) //
			.credentials(accessKey, secretKey) //

		if (ssl.enabled) {
			val httpClient = KeyStoreLoader.createHttpClient(ssl)
			return builder.httpClient(httpClient).build()
		}
		
		return builder.build()
	}

}