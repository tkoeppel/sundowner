package de.tkoeppel.sundowner.mapper

import de.tkoeppel.sundowner.po.SpotPO
import de.tkoeppel.sundowner.to.spots.CoordinateTO
import de.tkoeppel.sundowner.to.spots.MapSpotTO

class SpotMapper {
	fun mapMapSpot(po: SpotPO, averageRating: Double?): MapSpotTO {
		val point = po.location
		val coordinateTO = CoordinateTO(point.x, point.y)
		val name = po.name
		return MapSpotTO(po.id, coordinateTO, name, averageRating)
	}
}