package de.tkoeppel.sundowner.module.spots

import de.tkoeppel.sundowner.to.MapSpotTO
import org.assertj.core.api.Assertions.assertThat
import org.locationtech.jts.geom.Coordinate
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

class GetPointsInViewTest : SpotTestBase() {
	@Test
	fun `get empty points list`() {
		// pre
		create(name = "point 1", location = Coordinate(2.0, 2.0))
		create(name = "point 2", location = Coordinate(2.0, -2.0))
		create(name = "point 3", location = Coordinate(-2.0, 2.0))
		create(name = "point 4", location = Coordinate(-2.0, -2.0))

		// act
		val points = getPoints(10, -1.0, -1.0, 1.0, 1.0)

		// post
		assertThat(points).isEmpty()
	}

	private fun getPoints(
		limit: Int = 10, minX: Double = -1.0, minY: Double = -1.0, maxX: Double = 1.0, maxY: Double = 1.0
	): List<MapSpotTO> {
		val result = this.mockMvc.perform(
			get(GET_SPOTS_PATH).contentType(MediaType.APPLICATION_JSON).param("limit", "$limit").param("minX", "$minX")
				.param("minY", "$minY").param("maxX", "$maxX").param("maxY", "$maxY")
		).andExpect(status().isOk).andReturn()

		return this.convertToTO(result)
	}
}