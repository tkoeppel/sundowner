package de.tkoeppel.sundowner.to

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

/**
 * Transfer object of the spot immediately shown on the map with the necessary details.
 *
 * @property id The ID of the spot
 * @property location The location in latitude and longitude
 * @property name The name of the spot
 * @property avgRating The average rating of the spot
 * @constructor Create a
 */
@Schema(description = "Transfer object of the spot immediately shown on the map with the necessary details.")
data class MapSpotTO(

	@JsonProperty("id") override val id: Long,

	@JsonProperty("location") @Schema(description = "The location of the spot.") val location: CoordinateTO,

	@JsonProperty("name") @Schema(description = "The name given to the spot.") val name: String,

	@JsonProperty("avgRating") @Schema(
		description = "The overall rating of the spot.",
		minimum = "0",
		maximum = "10"
	) val avgRating: Double
) : BaseElementTO(id)