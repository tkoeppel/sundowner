package de.tkoeppel.sundowner.module.spots

import de.tkoeppel.sundowner.assertion.SpotAssert
import de.tkoeppel.sundowner.basetype.spots.SpotType
import de.tkoeppel.sundowner.basetype.spots.TransportType
import de.tkoeppel.sundowner.to.spots.CoordinateTO
import de.tkoeppel.sundowner.to.spots.CreateSpotTO
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class CreateSpotTest : SpotTestBase() {

	companion object {
		private const val LAT = 48.797767981336015
		private const val LNG = 9.801153029580314
		private const val DESCRIPTION = "If you go uphill you have a nice view"
		private val TRANSPORT = listOf<TransportType>(TransportType.BIKE, TransportType.BY_FOOT)
	}

	@WithUserDetails("user")
	@Test
	fun `create spot`() {
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
		SpotAssert.assertNewSpot(createSpotTO, po, this.user)
	}

	private fun createSpotRequest(createSpot: CreateSpotTO): Long {
		val result = this.mockMvc.perform(
			post(CREATE_SPOT_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(createSpot))
		).andExpect(status().isOk).andReturn()

		return result.response.contentAsString.toLong()
	}

}