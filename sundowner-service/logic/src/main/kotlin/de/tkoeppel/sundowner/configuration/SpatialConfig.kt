package de.tkoeppel.sundowner.configuration

import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.n52.jackson.datatype.jts.JtsModule

@Configuration
class SpatialConfig {
	/**
	 * Geometry factory
	 * @return A [GeometryFactory] bean
	 */
	@Bean
	fun geometryFactory(): GeometryFactory {
		val precisionModel = PrecisionModel(PrecisionModel.FLOATING)
		return GeometryFactory(precisionModel)
	}

	@Bean
	fun jtsModule(): JtsModule {
		return JtsModule()
	}
}