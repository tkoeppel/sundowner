package de.tkoeppel.sundowner.module.spots

import de.tkoeppel.sundowner.api.SpotsApi
import de.tkoeppel.sundowner.to.MapSpotTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class SpotEndpoint : SpotsApi {

	@Autowired
	private lateinit var spotService: SpotService

	/**
	 * @see de.tkoeppel.sundowner.api.SpotsApi
	 */
	override fun getPointsInView(
		limit: Int, minX: Double, minY: Double, maxX: Double, maxY: Double
	): ResponseEntity<List<MapSpotTO>> {
		val points = this.spotService.getPointsInView(limit, minX, minY, maxX, maxY)
		return ResponseEntity.ok(points)
	}
}