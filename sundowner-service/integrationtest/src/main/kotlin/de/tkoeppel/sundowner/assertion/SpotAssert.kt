package de.tkoeppel.sundowner.assertion

import de.tkoeppel.sundowner.basetype.spots.SpotStatus
import de.tkoeppel.sundowner.po.SpotPO
import de.tkoeppel.sundowner.po.UserPO
import de.tkoeppel.sundowner.to.spots.CoordinateTO
import de.tkoeppel.sundowner.to.spots.CreateSpotTO
import de.tkoeppel.sundowner.to.spots.MapSpotTO
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.locationtech.jts.geom.Coordinate
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

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

	fun assertNewSpot(to: CreateSpotTO, po: SpotPO, user: UserPO) {
		assertThat(to.type).isEqualTo(po.type)
		assert(to.location, po.location)
		assertThat(to.description).isEqualTo(po.description)
		assertThat(user.id).isEqualTo(po.addedBy.id)
		assertThat(ZonedDateTime.now()).isCloseTo(po.addedDate, within(5, ChronoUnit.SECONDS))
		assertThat(to.transport).isEqualTo(po.transport)
		assertThat(SpotStatus.DRAFT).isEqualTo(po.status)
	}
}