package de.tkoeppel.sundowner.api

import de.tkoeppel.sundowner.to.jobs.Job
import de.tkoeppel.sundowner.to.jobs.JobTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Tag(
	name = "Jobs", description = "API handling all sundowner job requests."
)
@Validated
@RequestMapping("/api/v1/jobs")
interface JobsApi {

	@Operation(
		summary = "Get Job by ID",
		description = "Retrieves the details of a specific job using its unique identifier."
	)
	@ApiResponses(
		value = [
			ApiResponse(
				responseCode = "200",
				description = "Successfully retrieved job details",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = Schema(implementation = JobTO::class)
					)
				]
			),
			ApiResponse(
				responseCode = "404",
				description = "Job not found for the provided ID",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "400",
				description = "Invalid ID supplied",
				content = [Content()]
			)
		]
	)
	@GetMapping("/{id}")
	fun getJob(
		@Parameter(
			description = "The unique identifier of the job to retrieve.",
			required = true,
			example = "123"
		)
		@PathVariable("id")
		id: Long
	): JobTO


	@Operation(
		summary = "Get Job Result by ID",
		description = "Retrieves the result data of a specific completed job using its unique identifier."
	)
	@ApiResponses(
		value = [
			ApiResponse(
				responseCode = "200",
				description = "Successfully retrieved job result",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = Schema(implementation = Job::class)
					)
				]
			),
			ApiResponse(
				responseCode = "404",
				description = "Job or Job result not found for the provided ID",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "400",
				description = "Invalid ID supplied",
				content = [Content()]
			),
			ApiResponse(
				responseCode = "202",
				description = "Job is still processing, result not yet available",
				content = [Content()]
			)
		]
	)
	@GetMapping("/{id}/result")
	fun getJobResult(
		@Parameter(
			description = "The unique identifier of the job whose result is to be retrieved.",
			required = true,
			example = "123"
		)
		@PathVariable("id")
		id: Long
	): Job
}

