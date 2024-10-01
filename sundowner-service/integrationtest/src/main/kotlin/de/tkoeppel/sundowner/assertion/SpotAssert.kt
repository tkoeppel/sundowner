package de.tkoeppel.sundowner.assertion

import de.tkoeppel.sundowner.po.SpotPO
import de.tkoeppel.sundowner.to.MapSpotTO
import org.assertj.core.api.Assertions.assertThat

object SpotAssert {
	fun assert(po: SpotPO, to: MapSpotTO) {
		assertThat(to.id).isEqualTo(po.id)
		assertThat(to.name).isEqualTo(po.name)
		assertThat(to.location).isEqualTo(po.location)
		assertThat(to.avgRating).isEqualTo(po.averageRating)
	}
}