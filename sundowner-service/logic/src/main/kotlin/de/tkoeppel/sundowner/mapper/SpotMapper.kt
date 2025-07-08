package de.tkoeppel.sundowner.mapper

import de.tkoeppel.sundowner.so.MapSpotSO
import de.tkoeppel.sundowner.to.spots.CoordinateTO
import de.tkoeppel.sundowner.to.spots.MapSpotTO

class SpotMapper {
	fun mapMapSpot(so: MapSpotSO): MapSpotTO {
		val point = CoordinateTO(so.longitude, so.latitude)

		return MapSpotTO(so.id, point, so.name, so.avgRating?.toDouble())
	}
}