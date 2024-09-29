package de.tkoeppel.sundowner.module.spots

import de.tkoeppel.sundowner.SundownerServiceTestBase
import de.tkoeppel.sundowner.basetype.SpotType
import de.tkoeppel.sundowner.basetype.TransportType
import de.tkoeppel.sundowner.po.SpotPO
import org.locationtech.jts.geom.Coordinate
import java.time.ZonedDateTime

class SpotTestBase : SundownerServiceTestBase() {
	protected final val GET_PATH = "$API_PATH/spots"

	protected final val GET_SPOTS_PATH: String = GET_PATH

	protected fun create(
		type: SpotType = SpotType.SUNSET,
		location: Coordinate = Coordinate(1.0, 1.0),
		name: String = "My Spot",
		description: String = "This is a fun place",
		avgRating: Double = 9.9,
		addedBy: String = "sunset-enjoyer",
		addedDate: ZonedDateTime = ZonedDateTime.now(),
		transport: List<TransportType> = listOf<TransportType>(TransportType.BY_FOOT)
	): SpotPO {
		val spot = SpotPO(type, location, name, description, avgRating, addedBy, addedDate, transport)
		this.spotDAO.save(spot)
		return spot
	}

}