package de.tkoeppel.sundowner.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring.mvc.cors")
data class SpringMvcConfig(
	val mapping: String,
	val allowedOrigins: String,
	val allowedMethods: String,
	val allowedHeaders: String,
	val maxAge: Long,
	val allowCredentials: Boolean
)