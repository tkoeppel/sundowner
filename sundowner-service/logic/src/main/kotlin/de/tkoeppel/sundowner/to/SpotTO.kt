package de.tkoeppel.sundowner.to

import io.swagger.v3.oas.annotations.media.Schema
import org.locationtech.jts.geom.Coordinate

data class SpotTO(

	override val id: Long,

	@Schema(
		description = "The location of the spot.")
	val location: Coordinate,

	@Schema(
		description = "The name given to the spot.")
	val name: String,

	@Schema(
		description = "The overall rating of the spot.",
		minimum = "0",
		maximum = "10")
	val rating: Int
) : BaseElementTO(id)