package de.tkoeppel.sundowner.module.spots

import de.tkoeppel.sundowner.SundownerServiceTestBase
import de.tkoeppel.sundowner.basetype.spots.SpotStatus
import de.tkoeppel.sundowner.basetype.spots.SpotType
import de.tkoeppel.sundowner.basetype.spots.TransportType
import de.tkoeppel.sundowner.po.SpotPO
import de.tkoeppel.sundowner.po.SpotReviewPO
import de.tkoeppel.sundowner.po.UserPO
import org.locationtech.jts.geom.Coordinate
import java.time.ZonedDateTime

open class SpotTestBase : SundownerServiceTestBase() {
	protected val GET_PATH = "$API_PATH/spots"

	protected val GET_SPOTS_PATH: String = "$GET_PATH/public/"

	protected val CREATE_SPOT_PATH = "/api/spots"


	protected fun createSpot(
		type: SpotType = SpotType.SUNSET,
		location: Coordinate = Coordinate(0.0, 0.0),
		name: String = "My Spot",
		description: String = "This is a fun place",
		addedBy: UserPO = this.user,
		addedDate: ZonedDateTime = ZonedDateTime.now(),
		transport: List<TransportType> = listOf(TransportType.BY_FOOT, TransportType.BIKE),
		status: SpotStatus = SpotStatus.CONFIRMED
	): SpotPO {
		val spot = SpotPO(type, location, name, description, addedBy, addedDate, transport, status)
		this.spotDAO.save(spot)
		return spot
	}

	protected fun createReview(
		spot: SpotPO, //
		user: UserPO = this.user, //
		rating: Int = 0, //
		comment: String = "This is a comment"
	): SpotReviewPO {
		val spotReview = SpotReviewPO(spot, user, rating, comment)
		this.spotReviewDAO.save(spotReview)
		return spotReview

	}

}