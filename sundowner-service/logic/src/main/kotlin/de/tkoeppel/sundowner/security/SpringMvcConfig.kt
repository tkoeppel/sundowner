package de.tkoeppel.sundowner.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.mvc.cors")
data class SpringMvcConfig(
	val mapping: String,
	val allowedOrigins: String,
	val allowedHeaders: String,
	val maxAge: Long,
	val allowCredentials: Boolean
)