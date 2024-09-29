package de.tkoeppel.sundowner.mapper

import de.tkoeppel.sundowner.po.SpotPO
import de.tkoeppel.sundowner.to.CoordinateTO
import de.tkoeppel.sundowner.to.MapSpotTO

class SpotMapper {
	fun mapMapSpot(po: SpotPO): MapSpotTO {
		val point = po.location
		val coordinateTO = CoordinateTO(point.x, point.y)
		return MapSpotTO(po.id, coordinateTO, po.name, po.averageRating)
	}
}