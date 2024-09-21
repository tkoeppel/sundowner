package de.tkoeppel.sundowner.dummy

import de.tkoeppel.sundowner.SundownerServiceTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DummyTest : SundownerServiceTest() {

	@Test
	fun  testDummy() {
		assertThat(true).isTrue()
	}
}