package de.tkoeppel.sundowner.configuration

import org.n52.jackson.datatype.jts.JtsModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpatialConfig {

	@Bean
	fun jtsModule(): JtsModule {
		return JtsModule()
	}
}