package de.tkoeppel.sundowner.util

import de.tkoeppel.sundowner.basetype.spots.SpotType
import de.tkoeppel.sundowner.module.geocoding.ReverseGeoCodeDetails
import de.tkoeppel.sundowner.to.spots.CoordinateTO

object SpotNameUtil {

	fun getSpotName(type: SpotType, coordinate: CoordinateTO, details: ReverseGeoCodeDetails?): String {
		if (details == null) {
			return "${type.displayName} over somewhere"
		}
		
		val address = details.address
		return when {
			!address.attraction.isNullOrBlank() -> "${type.displayName} at ${address.attraction}"
			!address.road.isNullOrBlank() -> "${type.displayName} on ${address.road}"
			!address.road.isNullOrBlank() -> "${type.displayName} over ${address.suburb}"
			!address.neighbourhood.isNullOrBlank() -> "${type.displayName} over ${address.neighbourhood}"
			!address.city.isNullOrBlank() -> "${type.displayName} over ${address.city}"
			!address.state.isNullOrBlank() -> "${type.displayName} in ${address.state}"

			else -> "${type.displayName} over somewhere"
		}
	}
}