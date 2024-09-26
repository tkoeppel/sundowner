package de.tkoeppel.sundowner.module.dummy

import de.tkoeppel.sundowner.SundownerServiceTestBase
import de.tkoeppel.sundowner.basetype.SpotType
import de.tkoeppel.sundowner.basetype.TransportType
import de.tkoeppel.sundowner.po.SpotPO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.Coordinate
import java.time.ZonedDateTime

class DummyTest : SundownerServiceTestBase() {

	@Test
	fun  testDummy() {
		val loc =  Coordinate(3.3, 3.3)
		val point = this.geoFactory.createPoint(loc)
		val spot1 = SpotPO(SpotType.SUNSET, point,
			"", "", "", ZonedDateTime.now(), TransportType.CAR )
		val spot2 = SpotPO(SpotType.SUNSET, point,
			"", "", "", ZonedDateTime.now(), TransportType.CAR )
		this.spotDAO.save(spot1)
		this.spotDAO.save(spot2)

		assertThat(true).isTrue()
	}
}