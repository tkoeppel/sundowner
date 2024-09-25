package de.tkoeppel.sundowner.module.dummy

import de.tkoeppel.sundowner.SundownerServiceTestBase
import de.tkoeppel.sundowner.basetype.SpotType
import de.tkoeppel.sundowner.basetype.TransportType
import de.tkoeppel.sundowner.po.SpotPO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.geo.Point
import java.time.ZonedDateTime

class DummyTest : SundownerServiceTestBase() {

	@Test
	fun  testDummy() {
		val spot1 = SpotPO(SpotType.SUNSET, Point(3.3, 2.1),
			"", "", "", ZonedDateTime.now(), TransportType.CAR )
		// this.spotDAO.save(spot1)

		assertThat(true).isTrue()
	}
}