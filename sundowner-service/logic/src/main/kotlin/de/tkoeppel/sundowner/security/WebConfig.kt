package de.tkoeppel.sundowner.security

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebConfig(private val springMvcConfig: SpringMvcConfig) : WebMvcConfigurer {

	override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping(springMvcConfig.mapping) //
			.allowedOrigins(springMvcConfig.allowedOrigins) //
			.allowedHeaders(springMvcConfig.allowedHeaders) //
			.allowCredentials(springMvcConfig.allowCredentials) //
			.maxAge(springMvcConfig.maxAge)
	}
}