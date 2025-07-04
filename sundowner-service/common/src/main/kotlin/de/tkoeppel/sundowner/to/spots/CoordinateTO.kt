package de.tkoeppel.sundowner.to.spots

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Coordinate object containing the longitude and latitude
 *
 * @property lng The longitude of the coordinate
 * @property lat The latitude of the coordinate
 * @constructor Create empty Coordinate
 */
@Schema(description = "Transfer object for a simple longitude and latitude coordinate.")
data class CoordinateTO(
	@Schema(description = "The longitude of the coordinate.", type = "Double") val lng: Double,

	@Schema(description = "The latitude of the coordinate.", type = "Double") val lat: Double
)