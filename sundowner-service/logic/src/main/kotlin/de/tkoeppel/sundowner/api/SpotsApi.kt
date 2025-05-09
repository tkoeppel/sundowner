package de.tkoeppel.sundowner.api

import de.tkoeppel.sundowner.to.spots.MapSpotTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Tag(
	name = "Spots", description = "API handling all sundowner spot requests."
)
@Validated
@RequestMapping("/api/v1/spots")
interface SpotsApi {

	@Operation(
		summary = "Get the most relevant points of the provided map viewport.",
		description = "Retrieves a list of points of interest (spots) located within the given bounding box and filtered based on zoom level and center coordinates.",
		responses = [ApiResponse(
			responseCode = "200", description = "Successful operation", content = [Content(
				mediaType = "application/json", schema = Schema(
					implementation = Array<MapSpotTO>::class
				)
			)]
		), ApiResponse(responseCode = "400", description = "Bad Request - Invalid request parameters")],
		operationId = "getPointsInView",
		parameters = [Parameter("limit"), Parameter("minX"), Parameter("minY"), Parameter("maxX"), Parameter("maxY")]
	)
	@GetMapping()
	fun getPointsInView(
		@RequestParam(required = true, defaultValue = "10") limit: Int,
		@RequestParam(required = true) minX: Double,
		@RequestParam(required = true) minY: Double,
		@RequestParam(required = true) maxX: Double,
		@RequestParam(required = true) maxY: Double
	): ResponseEntity<List<MapSpotTO>>
}