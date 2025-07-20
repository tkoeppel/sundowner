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
		assertThat(po.id).isEqualTo(to.id)
		assertThat(po.name).isEqualTo(to.name)
		assert(po.location, to.location)
		assertThat(to.avgRating).isEqualTo(avgRating)
	}

	fun assert(po: Coordinate, to: CoordinateTO) {
		assertThat(po.x).isEqualTo(to.lng)
		assertThat(po.y).isEqualTo(to.lat)
	}

	fun assertNewSpot(to: CreateSpotTO, po: SpotPO, user: UserPO, name: String) {
		assertThat(po.type).isEqualTo(to.type)
		assertThat(po.description).isEqualTo(to.description)
		assertThat(po.transport).containsExactlyInAnyOrderElementsOf(to.transport)
		assertThat(po.name).isEqualTo(name)
		assertThat(po.status).isEqualTo(SpotStatus.DRAFT)
		assertThat(po.addedBy.id).isEqualTo(user.id)
		assertThat(po.location.x).isEqualTo(to.location.lng)
		assertThat(po.location.y).isEqualTo(to.location.lat)
		assertThat(po.addedAt).isCloseTo(ZonedDateTime.now(), within(5, ChronoUnit.SECONDS))
	}
}