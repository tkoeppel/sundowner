package de.tkoeppel.sundowner.to

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Transfer object for a simple longitude and latitude coordinate.")
data class CoordinateTO(
	@Schema(description = "The longitude of the coordinate.", type = "Double") val lng: Double,

	@Schema(description = "The latitude of the coordinate.", type = "Double") val lat: Double
)