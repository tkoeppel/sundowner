package de.tkoeppel.sundowner.module.spots

import de.tkoeppel.sundowner.api.SpotsApi
import de.tkoeppel.sundowner.to.spots.CreateSpotTO
import de.tkoeppel.sundowner.to.spots.MapSpotTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController

@RestController
class SpotEndpoint : SpotsApi {

	@Autowired
	private lateinit var spotService: SpotService

	/** @see de.tkoeppel.sundowner.api.SpotsApi#getPointsInView */
	@PreAuthorize("permitAll()")
	override fun getPointsInView(
		limit: Int, minX: Double, minY: Double, maxX: Double, maxY: Double
	): ResponseEntity<List<MapSpotTO>> {
		val points = this.spotService.getPointsInView(limit, minX, minY, maxX, maxY)
		return ResponseEntity.ok(points)
	}

	/** @see de.tkoeppel.sundowner.api.SpotsApi#createSpot */
	@PreAuthorize("hasRole('ROLE_USER')")
	override fun createSpot(createSpotTO: CreateSpotTO): ResponseEntity<Long> {
		val spotId = this.spotService.createSpot(createSpotTO)
		return ResponseEntity.ok(spotId)
	}
}
