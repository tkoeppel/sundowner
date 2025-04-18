package de.tkoeppel.sundowner.module.spots

import com.fasterxml.jackson.core.type.TypeReference
import de.tkoeppel.sundowner.assertion.SpotAssert
import de.tkoeppel.sundowner.po.SpotPO
import de.tkoeppel.sundowner.to.spots.MapSpotTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.locationtech.jts.geom.Coordinate
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

class GetPointsInViewTest : SpotTestBase() {
	companion object {
		private const val DEFAULT_LIMIT = 10
		private const val DEFAULT_MIN_X = -1.0
		private const val DEFAULT_MIN_Y = -1.0
		private const val DEFAULT_MAX_X = 1.0
		private const val DEFAULT_MAX_Y = 1.0
	}


	@Test
	fun `get single point`() {
		// pre
		val po = create(name = "point", location = Coordinate(0.0, 0.0))

		// act
		val tos = getPoints()

		// post
		assertThat(tos.size).isEqualTo(1)
		SpotAssert.assert(tos[0], po)
	}

	@Test
	fun `get multiple points`() {
		// pre
		val amount = 5
		val pos = mutableListOf<SpotPO>()
		for (i in 0..<amount) {
			val coord = i * 00.1
			pos.add(create(name = "point$i", location = Coordinate(coord, coord), avgRating = 10.0 - i))
		}

		// act
		val tos = getPoints()

		// post
		assertThat(tos.size).isEqualTo(amount)
		for (i in 0..<amount) {
			SpotAssert.assert(tos[i], pos[i])
		}
	}

	@Test
	fun `get some points`() {
		// pre
		val po = create(name = "point 1", location = Coordinate(0.0, 0.0))
		create(name = "point 2", location = Coordinate(2.0, 2.0))

		// act
		val tos = getPoints()

		// post
		assertThat(tos.size).isEqualTo(1)
		SpotAssert.assert(tos[0], po)
	}

	@Test
	fun `get points outside of box`() {
		// pre
		create(name = "point 1", location = Coordinate(2.0, 2.0))
		create(name = "point 2", location = Coordinate(2.0, -2.0))
		create(name = "point 3", location = Coordinate(-2.0, 2.0))
		create(name = "point 4", location = Coordinate(-2.0, -2.0))

		// act
		val tos = getPoints()

		// post
		assertThat(tos).isEmpty()
	}

	@Test
	fun `get no points`() {
		// pre

		// act
		val tos = getPoints()

		// post
		assertThat(tos).isEmpty()
	}

	@Test
	fun `get points on edge`() {
		// pre
		val po1 = create(name = "point 1", location = Coordinate(1.0, 1.0), avgRating = 10.0)
		val po2 = create(name = "point 2", location = Coordinate(1.0, -1.0), avgRating = 9.0)
		val po3 = create(name = "point 3", location = Coordinate(-1.0, 1.0), avgRating = 8.0)
		val po4 = create(name = "point 4", location = Coordinate(-1.0, -1.0), avgRating = 7.0)

		// act
		val tos = getPoints()

		// post
		assertThat(tos.size).isEqualTo(4)
		SpotAssert.assert(tos[0], po1)
		SpotAssert.assert(tos[1], po2)
		SpotAssert.assert(tos[2], po3)
		SpotAssert.assert(tos[3], po4)
	}

	@Test
	fun `get point with invalid box`() {
		// pre
		create(name = "point", location = Coordinate(0.0, 0.0))

		// act
		val tos = getPoints(10, 1.0, 1.0, -1.0 - 1.0)

		// post
		assertThat(tos).isEmpty()
	}

	@Test
	fun `get points with 0 limit`() {
		// pre
		create(name = "point", location = Coordinate(0.0, 0.0))

		// act
		val tos = getPoints(limit = 0)

		// post
		assertThat(tos).isEmpty()
	}

	@ParameterizedTest
	@ValueSource(strings = ["-1", "100000"])
	fun `get points with invalid limit`(limit: String) {
		// pre
		create(name = "point", location = Coordinate(0.0, 0.0))

		// act, post
		val builder = get(GET_SPOTS_PATH).contentType(MediaType.APPLICATION_JSON).param("limit", limit)
			.param("minX", "$DEFAULT_MIN_X").param("minY", "$DEFAULT_MIN_Y").param("maxX", "$DEFAULT_MAX_X")
			.param("maxY", "$DEFAULT_MAX_Y")
		doTestBadRequest(builder, "Limit must be between 0 and")
	}

	@Test
	fun `get points with limit`() {
		// pre
		val limit = 1
		val po = create(name = "point 1", location = Coordinate(0.0, 0.0), avgRating = 10.0)
		create(name = "point 2", location = Coordinate(0.0, 0.0), avgRating = 0.0)

		// act
		val tos = getPoints(limit = limit)

		// post
		assertThat(tos.size).isEqualTo(limit)
		SpotAssert.assert(tos[0], po)
	}

	private fun getPoints(
		limit: Int = DEFAULT_LIMIT,
		minX: Double = DEFAULT_MIN_X,
		minY: Double = DEFAULT_MIN_Y,
		maxX: Double = DEFAULT_MAX_X,
		maxY: Double = DEFAULT_MAX_Y
	): List<MapSpotTO> {
		val result = this.mockMvc.perform(
			get(GET_SPOTS_PATH).contentType(MediaType.APPLICATION_JSON).param("limit", "$limit").param("minX", "$minX")
				.param("minY", "$minY").param("maxX", "$maxX").param("maxY", "$maxY")
		).andExpect(status().isOk).andReturn()

		return this.convertToTO(result, object : TypeReference<List<MapSpotTO>>() {})
	}
}