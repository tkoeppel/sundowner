package de.tkoeppel.sundowner.to.jobs

import java.time.ZonedDateTime

data class FailJobResult (
	override val id: Long,
	override val name: String,
	override val timestamp: ZonedDateTime,
	val userFriendlyMessage: String
) : Job
