package de.tkoeppel.sundowner.to

import io.swagger.v3.oas.annotations.media.Schema
import org.locationtech.jts.geom.Coordinate

data class MapViewTO (
	@Schema(
		description = "The bounding box string of the map viewport.",
		format = "minX,minY,maxX,maxY",
		requiredMode = Schema.RequiredMode.REQUIRED)
	val bbox: String,

	@Schema(
		description = "The zoom level used for the map.",
		minimum = "0",
		maximum = "19",
		requiredMode = Schema.RequiredMode.REQUIRED)
	val zoom: Int,

	@Schema(
		description = "The center of the map viewport with its longitude and latitude.")
	val center: Coordinate
)