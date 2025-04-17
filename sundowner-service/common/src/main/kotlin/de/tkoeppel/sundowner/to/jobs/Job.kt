package de.tkoeppel.sundowner.to.jobs

import de.tkoeppel.sundowner.to.BaseElementTO
import java.time.ZonedDateTime

sealed interface Job : BaseElementTO {
	val name: String
	val timestamp: ZonedDateTime
}