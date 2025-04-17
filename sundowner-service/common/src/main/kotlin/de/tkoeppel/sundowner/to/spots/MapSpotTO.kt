package de.tkoeppel.sundowner.to.spots

import de.tkoeppel.sundowner.to.BaseElementTO
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
	@Schema(description = "The ID of the element.") override val id: Long,

	@Schema(description = "The location of the spot.") val location: CoordinateTO,

	@Schema(description = "The name given to the spot.") val name: String,

	@Schema(
		description = "The overall rating of the spot.", minimum = "0", maximum = "10"
	) val avgRating: Double
) : BaseElementTO