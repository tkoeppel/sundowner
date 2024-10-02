package de.tkoeppel.sundowner.assertion

import de.tkoeppel.sundowner.po.SpotPO
import de.tkoeppel.sundowner.to.CoordinateTO
import de.tkoeppel.sundowner.to.MapSpotTO
import org.assertj.core.api.Assertions.assertThat
import org.locationtech.jts.geom.Coordinate

object SpotAssert {
	fun assert(to: MapSpotTO, po: SpotPO) {
		assertThat(to.id).isEqualTo(po.id)
		assertThat(to.name).isEqualTo(po.name)
		assert(to.location, po.location)
		assertThat(to.avgRating).isEqualTo(po.averageRating)
	}

	fun assert(to: CoordinateTO, po: Coordinate) {
		assertThat(to.long).isEqualTo(po.x)
		assertThat(to.lat).isEqualTo(po.y)
	}
}