package de.tkoeppel.sundowner.dummy

import de.tkoeppel.sundowner.SundownerServiceTest
import de.tkoeppel.sundowner.po.UserPO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

class DummyTest : SundownerServiceTest() {

	@Test
	fun  testDummy() {
		val po = UserPO(ZonedDateTime.now(),ZonedDateTime.now(), "dasd" )
		val po2 = UserPO(ZonedDateTime.now(),ZonedDateTime.now(), "daklknsd" )
		this.userDAO.save(po)
		this.userDAO.save(po2)

		assertThat(true).isTrue()
	}
}