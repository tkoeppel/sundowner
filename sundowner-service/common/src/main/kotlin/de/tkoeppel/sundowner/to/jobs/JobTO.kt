package de.tkoeppel.sundowner.to.jobs

import de.tkoeppel.sundowner.basetype.jobs.JobState
import io.swagger.v3.oas.annotations.media.Schema
import java.time.ZonedDateTime

@Schema(description = "The job details")
data class JobTO(
	@Schema(description = "The job ID") override val id: Long,

	@Schema(description = "The job name") override val name: String,

	@Schema(description = "The last time the job was updated") override val timestamp: ZonedDateTime,

	@Schema(description = "The job state") val state: JobState,

	@Schema(description = "The progress of the job") val progress: Double = 0.0,

	@Schema(description = "The progress context of the job") val progressContext: String = ""
) : Job
