package de.tkoeppel.sundowner.to.spots

import de.tkoeppel.sundowner.basetype.SpotType
import de.tkoeppel.sundowner.basetype.TransportType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
	name = "CreateSpotRequest",
	description = "Request body for creating a new sunset spot on the map."
)
data class CreateSpotTO(
	@Schema(
		description = "The type of spot (e.g. sunrise or sunset)",
		required = true
	)
	val type: SpotType,

	@Schema(
		description = "The geographical coordinates of the sunset spot.",
		required = true
	)
	val location: CoordinateTO,

	@Schema(
		description = "A user-provided description of the spot (e.g., 'Quiet place with a great view over the valley').",
		maxLength = 500,
		example = "Perfect view of the old town during sunset."
	)
	val description: String?,

	@Schema(
		description = "A list of recommended transport methods to reach the spot.",
		required = true
	)
	val transport: List<TransportType>
)
