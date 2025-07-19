package de.tkoeppel.sundowner.module.spots

import com.fasterxml.jackson.core.type.TypeReference
import de.tkoeppel.sundowner.assertion.SpotAssert
import de.tkoeppel.sundowner.basetype.spots.SpotStatus
import de.tkoeppel.sundowner.basetype.spots.SpotType
import de.tkoeppel.sundowner.po.SpotPO
import de.tkoeppel.sundowner.to.spots.MapSpotTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.locationtech.jts.geom.Coordinate
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

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
		val po = createSpot(name = "point", location = Coordinate(0.0, 0.0))

		// act
		val tos = getPointsRequest()

		// post
		assertThat(tos.size).isEqualTo(1)
		SpotAssert.assert(tos[0], po, null)
	}

	@Test
	fun `get multiple points`() {
		// pre
		val amount = 5
		val pos = mutableListOf<SpotPO>()
		for (i in 0..<amount) {
			val coordinate = i * 00.1
			val spot = createSpot(name = "point$i", location = Coordinate(coordinate, coordinate))
			createReview(spot = spot, rating = 5 - i)
			pos.add(spot)
		}

		// act
		val tos = getPointsRequest()

		// post
		assertThat(tos.size).isEqualTo(amount)
		for (i in 0..<amount) {
			SpotAssert.assert(tos[i], pos[i], (5 - i).toDouble())
		}
	}

	@Test
	fun `get some points`() {
		// pre
		val po = createSpot(name = "point 1", location = Coordinate(0.0, 0.0))
		createSpot(name = "point 2", location = Coordinate(2.0, 2.0))

		// act
		val tos = getPointsRequest()

		// post
		assertThat(tos.size).isEqualTo(1)
		SpotAssert.assert(tos[0], po, null)
	}

	@Test
	fun `get points outside of box`() {
		// pre
		createSpot(name = "point 1", location = Coordinate(2.0, 2.0))
		createSpot(name = "point 2", location = Coordinate(2.0, -2.0))
		createSpot(name = "point 3", location = Coordinate(-2.0, 2.0))
		createSpot(name = "point 4", location = Coordinate(-2.0, -2.0))

		// act
		val tos = getPointsRequest()

		// post
		assertThat(tos).isEmpty()
	}

	@Test
	fun `get no points`() {
		// pre

		// act
		val tos = getPointsRequest()

		// post
		assertThat(tos).isEmpty()
	}

	@Test
	fun `get points on edge`() {
		// pre
		val po1 = createSpot(name = "point 1", location = Coordinate(1.0, 1.0))
		createReview(spot = po1, rating = 5)
		val po2 = createSpot(name = "point 2", location = Coordinate(1.0, -1.0))
		createReview(spot = po2, rating = 4)
		val po3 = createSpot(name = "point 3", location = Coordinate(-1.0, 1.0))
		createReview(spot = po3, rating = 3)
		val po4 = createSpot(name = "point 4", location = Coordinate(-1.0, -1.0))
		createReview(spot = po4, rating = 2)

		// act
		val tos = getPointsRequest()

		// post
		assertThat(tos.size).isEqualTo(4)
		SpotAssert.assert(tos[0], po1, 5.0)
		SpotAssert.assert(tos[1], po2, 4.0)
		SpotAssert.assert(tos[2], po3, 3.0)
		SpotAssert.assert(tos[3], po4, 2.0)
	}

	@Test
	fun `get point with invalid box`() {
		// pre
		createSpot(name = "point", location = Coordinate(0.0, 0.0))

		// act
		val tos = getPointsRequest(10, 1.0, 1.0, -1.0 - 1.0)

		// post
		assertThat(tos).isEmpty()
	}

	@Test
	fun `get points with 0 limit`() {
		// pre
		createSpot(name = "point", location = Coordinate(0.0, 0.0))

		// act
		val tos = getPointsRequest(limit = 0)

		// post
		assertThat(tos).isEmpty()
	}

	@ParameterizedTest
	@ValueSource(strings = ["-1", "100000"])
	fun `get points with invalid limit`(limit: String) {
		// pre
		createSpot(name = "point", location = Coordinate(0.0, 0.0))

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
		val po = createSpot(name = "point 1", location = Coordinate(DEFAULT_MIN_X, 0.0))
		createReview(spot = po, rating = 5)
		val po2 = createSpot(name = "point 2", location = Coordinate(DEFAULT_MAX_X, 0.0))
		createReview(spot = po2, rating = 0)
		createSpot(name = "point 3", location = Coordinate(0.0, DEFAULT_MIN_Y))


		// act
		val tos = getPointsRequest(limit = limit)

		// post
		assertThat(tos.size).isEqualTo(limit)
		SpotAssert.assert(tos[0], po, 5.0)
	}

	@Test
	fun `get point with specific average rating`() {
		// pre
		val expectedRating = 3.5
		val po = createSpot(name = "point with rating", location = Coordinate(0.5, 0.5))
		createReview(spot = po, rating = 3)
		createReview(spot = po, rating = 4)


		// act
		val tos = getPointsRequest()

		// post
		assertThat(tos).hasSize(1)
		val resultTO = tos.first()
		SpotAssert.assert(resultTO, po, 3.5)
		assertThat(resultTO.avgRating).isEqualTo(expectedRating)
	}

	@Test
	fun `get point with no reviews should have null rating`() {
		// pre
		val po = createSpot(name = "point without rating", location = Coordinate(0.5, 0.5))

		// act
		val tos = getPointsRequest()

		// post
		assertThat(tos).hasSize(1)
		val resultTO = tos.first()
		SpotAssert.assert(resultTO, po, null)
		assertThat(resultTO.avgRating).isNull()
	}

	@Test
	fun `get points with and without ratings`() {
		// pre
		val poWithRating1 = createSpot(name = "point 1", location = Coordinate(0.1, 0.1))
		createReview(poWithRating1, rating = 5)
		val poWithoutRating =
			createSpot(name = "point 2", location = Coordinate(0.2, 0.2)) // No avgRating -> no reviews
		val poWithRating2 = createSpot(name = "point 3", location = Coordinate(0.3, 0.3))
		createReview(poWithRating2, rating = 4)

		// act
		val tos = getPointsRequest()

		// post
		assertThat(tos).hasSize(3)
		SpotAssert.assert(tos[0], poWithRating1, 5.0)
		SpotAssert.assert(tos[1], poWithRating2, 4.0)
		SpotAssert.assert(tos[2], poWithoutRating, null)
	}

	@Test
	fun `get only confirmed points and ignore others`() {
		// pre
		val confirmedSpot = createSpot(
			name = "A confirmed spot", location = Coordinate(0.1, 0.1), status = SpotStatus.CONFIRMED
		)
		createSpot(
			name = "A pending spot", location = Coordinate(0.2, 0.2), status = SpotStatus.PENDING
		)

		// act
		val tos = getPointsRequest()

		// post
		assertThat(tos).hasSize(1)
		SpotAssert.assert(tos[0], confirmedSpot, null)
	}

	@Test
	fun `get only sunset type points and ignore others`() {
		// pre
		val sunsetSpot = createSpot(
			name = "A sunset spot", location = Coordinate(0.1, 0.1), type = SpotType.SUNSET
		)
		createSpot(
			name = "A sunrise spot", location = Coordinate(0.2, 0.2), type = SpotType.SUNRISE
		)

		// act
		val tos = getPointsRequest()

		// post
		assertThat(tos).hasSize(1)
		SpotAssert.assert(tos[0], sunsetSpot, null)
	}

	@Test
	fun `get only points that are confirmed and of type sunset`() {
		// pre
		val expectedSpot = createSpot(
			name = "The one", location = Coordinate(0.1, 0.1), status = SpotStatus.CONFIRMED, type = SpotType.SUNSET
		)
		// Other spots that should be filtered out
		createSpot(
			name = "Pending sunset",
			location = Coordinate(0.2, 0.2),
			status = SpotStatus.PENDING,
			type = SpotType.SUNSET
		)
		createSpot(
			name = "Confirmed sunrise",
			location = Coordinate(0.3, 0.3),
			status = SpotStatus.CONFIRMED,
			type = SpotType.SUNRISE
		)

		// act
		val tos = getPointsRequest()

		// post
		assertThat(tos).hasSize(1)
		SpotAssert.assert(tos[0], expectedSpot, null)
	}

	@WithUserDetails("user")
	@Test
	fun `get points as user`() {
		// pre
		val po = createSpot(name = "point", location = Coordinate(0.0, 0.0))

		// act
		val tos = getPointsRequest()

		// post
		assertThat(tos.size).isEqualTo(1)
		SpotAssert.assert(tos[0], po, null)
	}

	@WithUserDetails("admin")
	@Test
	fun `get points as admin`() {
		// pre
		val po = createSpot(name = "point", location = Coordinate(0.0, 0.0))

		// act
		val tos = getPointsRequest()

		// post
		assertThat(tos.size).isEqualTo(1)
		SpotAssert.assert(tos[0], po, null)
	}

	private fun getPointsRequest(
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