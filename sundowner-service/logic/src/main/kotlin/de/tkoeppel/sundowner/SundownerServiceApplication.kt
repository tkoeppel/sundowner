package de.tkoeppel.sundowner

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@ConfigurationPropertiesScan
@ComponentScan("de.tkoeppel.sundowner")
@EntityScan(basePackages = ["de.tkoeppel.sundowner.po"])
@EnableJpaRepositories(basePackages = ["de.tkoeppel.sundowner.dao"])
@EnableWebSecurity
@EnableMethodSecurity
@EnableScheduling
@SpringBootApplication
class SundownerServiceApplication {

	@Bean
	fun customOpenAPI(): OpenAPI {
		return OpenAPI().info(
			Info().title("Sundowner Service API").description("This is the API for the Sundowner Service.")
				.version("v1")
		)
	}
}

fun main(args: Array<String>) {
	runApplication<SundownerServiceApplication>(*args)
}
