package de.tkoeppel.sundowner.to

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Transfer object for a simple longitude and latitude coordinate.")
data class CoordinateTO(
	@JsonProperty("long") @Schema(description = "The longitude of the coordinate.", type = "Double") val long: Double,

	@JsonProperty("lat") @Schema(description = "The latitude of the coordinate.", type = "Double") val lat: Double
)