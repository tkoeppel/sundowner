package de.tkoeppel.sundowner.assertion

import de.tkoeppel.sundowner.po.SpotPO
import de.tkoeppel.sundowner.to.spots.CoordinateTO
import de.tkoeppel.sundowner.to.spots.MapSpotTO
import org.assertj.core.api.Assertions.assertThat
import org.locationtech.jts.geom.Coordinate

object SpotAssert {
	fun assert(to: MapSpotTO, po: SpotPO, avgRating: Double?) {
		assertThat(to.id).isEqualTo(po.id)
		assertThat(to.name).isEqualTo(po.name)
		assert(to.location, po.location)
		assertThat(to.avgRating).isEqualTo(avgRating)
	}

	fun assert(to: CoordinateTO, po: Coordinate) {
		assertThat(to.lng).isEqualTo(po.x)
		assertThat(to.lat).isEqualTo(po.y)
	}
}