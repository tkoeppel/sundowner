package de.tkoeppel.sundowner.module.spots

import de.tkoeppel.sundowner.assertion.SpotAssert
import de.tkoeppel.sundowner.basetype.spots.SpotType
import de.tkoeppel.sundowner.basetype.spots.TransportType
import de.tkoeppel.sundowner.module.geocoding.GeoCodingService
import de.tkoeppel.sundowner.module.geocoding.ReverseGeoCodeDetails
import de.tkoeppel.sundowner.to.spots.CoordinateTO
import de.tkoeppel.sundowner.to.spots.CreateSpotTO
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.locationtech.jts.geom.Coordinate
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.File
import java.nio.file.Files


class CreateSpotTest : SpotTestBase() {

	private lateinit var geoCodingService: GeoCodingService

	companion object {
		private const val LAT = 48.797922
		private const val LNG = 9.8002968
		private const val DESCRIPTION = "If you go uphill you have a nice view"
		private val TRANSPORT = listOf<TransportType>(TransportType.BIKE, TransportType.BY_FOOT)
		private const val NAME = "Sunset on Aussichtsplattform Zeiselberg"
	}

	@BeforeEach
	public fun initGeoCodingMock() {
		this.geoCodingService = mock(GeoCodingService::class.java)
		val resource: File = ClassPathResource(
			"ReverseGeoCodeZeiselberg.json"
		).getFile()
		val revGeoCodeDetailsStr = String(
			Files.readAllBytes(resource.toPath())
		)
		val revGeoCodeDetails = this.mapper.readValue(revGeoCodeDetailsStr, ReverseGeoCodeDetails::class.java)
		`when`(this.geoCodingService.reverseGeocode(any())).thenReturn(revGeoCodeDetails)
	}

	@Transactional
	@Test
	fun `create spot as anonymous`() {
		// pre
		val createSpotTO = CreateSpotTO(
			SpotType.SUNSET,
			CoordinateTO(LNG, LAT),
			DESCRIPTION,
			TRANSPORT,
		)

		// act
		val reqBuilder = buildCreateSpotRequest(createSpotTO)
		doTestUnauthorized(reqBuilder)
	}

	@Transactional
	@WithUserDetails("user")
	@Test
	fun `create spot as user`() {
		// pre
		val createSpotTO = CreateSpotTO(
			SpotType.SUNSET,
			CoordinateTO(LNG, LAT),
			DESCRIPTION,
			TRANSPORT,
		)

		// act
		val id = createSpotRequest(createSpotTO)

		// post
		val po = this.spotDAO.getReferenceById(id)
		SpotAssert.assertNewSpot(createSpotTO, po, this.user, NAME)
	}

	@Transactional
	@WithUserDetails("admin")
	@Test
	fun `create spot as admin`() {
		// pre
		val createSpotTO = CreateSpotTO(
			SpotType.SUNSET,
			CoordinateTO(LNG, LAT),
			DESCRIPTION,
			TRANSPORT,
		)

		// act
		val id = createSpotRequest(createSpotTO)

		// post
		val po = this.spotDAO.getReferenceById(id)
		SpotAssert.assertNewSpot(createSpotTO, po, this.admin, NAME)
	}

	@Transactional
	@WithUserDetails("user")
	@ParameterizedTest
	@CsvSource("-180.1, 90.0", "180.0, -91.0", "180.1, -90", "-180.0, 90.1")
	fun `create spot with invalid coordinates`(lng: Double, lat: Double) {
		// pre
		val createSpotTO = CreateSpotTO(
			SpotType.SUNSET,
			CoordinateTO(lng, lat),
			DESCRIPTION,
			TRANSPORT,
		)

		// act
		val reqBuilder = buildCreateSpotRequest(createSpotTO)
		doTestBadRequest(reqBuilder, "Invalid coordinates")
	}

	@Transactional
	@WithUserDetails("user")
	@Test
	fun `create spot near another spot`() {
		// pre
		createSpot(location = Coordinate(LNG, LAT))
		val createSpotTO = CreateSpotTO(
			SpotType.SUNSET,
			CoordinateTO(LNG, LAT),
			DESCRIPTION,
			TRANSPORT,
		)

		// act
		val reqBuilder = buildCreateSpotRequest(createSpotTO)
		doTestBadRequest(reqBuilder, "There is already a spot within 10 meters of the given location")
	}

	@Transactional
	@WithUserDetails("user")
	@Test
	fun `create spot far from other spots`() {
		// pre
		createSpot(location = Coordinate(LNG + 1, LAT + 1))
		createSpot(location = Coordinate(LNG - 1, LAT - 1))
		val createSpotTO = CreateSpotTO(
			SpotType.SUNSET,
			CoordinateTO(LNG, LAT),
			DESCRIPTION,
			TRANSPORT,
		)

		// act
		val id = createSpotRequest(createSpotTO)

		// post
		val reqBuilder = buildCreateSpotRequest(createSpotTO)
		doTestBadRequest(reqBuilder, "There is already a spot within 10 meters of the given location")
	}

	@Transactional
	@WithUserDetails("user")
	@Test
	fun `create spot in international waters`() {
		// pre
		val createSpotTO = CreateSpotTO(
			SpotType.SUNSET,
			CoordinateTO(47.79268671439906, -32.42979289176831),
			DESCRIPTION,
			TRANSPORT,
		)

		// act
		val id = createSpotRequest(createSpotTO)

		// post
		val po = this.spotDAO.getReferenceById(id)
		SpotAssert.assertNewSpot(createSpotTO, po, this.user, "Sunset over somewhere")
	}


	private fun buildCreateSpotRequest(createSpotTO: CreateSpotTO): MockHttpServletRequestBuilder {
		return post(CREATE_SPOT_PATH).contentType(MediaType.APPLICATION_JSON)
			.content(this.mapper.writeValueAsString(createSpotTO))
	}

	private fun createSpotRequest(createSpotTO: CreateSpotTO): Long {
		val result = this.mockMvc.perform(
			buildCreateSpotRequest(createSpotTO)
		).andExpect(status().isOk).andReturn()

		return result.response.contentAsString.toLong()
	}

}