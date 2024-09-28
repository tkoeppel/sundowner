package de.tkoeppel.sundowner.to

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "The basic sundowner transport element with an ID.")
open class BaseElementTO(
	@Schema(description = "The ID of the element.") open val id: Long
)