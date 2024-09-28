package de.tkoeppel.sundowner.module.spots

import de.tkoeppel.sundowner.api.SpotsApi
import de.tkoeppel.sundowner.to.MapViewTO
import de.tkoeppel.sundowner.to.SpotTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

class SpotEndpoint : SpotsApi {

	@Autowired
	private lateinit var spotService: SpotService

	/**
	 * @see de.tkoeppel.sundowner.api.SpotsApi
	 */
	override fun getPointsInView(max: Int, mapViewTO: MapViewTO): ResponseEntity<List<SpotTO>> {
		val points = this.spotService.getPointsInView(max, mapViewTO)
		return ResponseEntity.ok(points)
	}
}