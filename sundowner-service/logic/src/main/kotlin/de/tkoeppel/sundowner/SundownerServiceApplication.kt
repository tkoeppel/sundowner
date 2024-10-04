package de.tkoeppel.sundowner

import de.tkoeppel.sundowner.configuration.SpringMvcConfig
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableConfigurationProperties(SpringMvcConfig::class)
@EntityScan(basePackages = ["de.tkoeppel.sundowner.po"])
@EnableJpaRepositories(basePackages = ["de.tkoeppel.sundowner.dao"])
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
