package de.tkoeppel.sundowner.module.geocoding

import com.fasterxml.jackson.annotation.JsonProperty

data class ReverseGeoCodeDetails(
	@JsonProperty("place_id") val placeId: String,

	@JsonProperty("licence") val licence: String,

	@JsonProperty("osm_type") val osmType: String,

	@JsonProperty("osm_id") val osmId: String,

	@JsonProperty("lat") val lat: String,

	@JsonProperty("lon") val lon: String,

	@JsonProperty("display_name") val displayName: String,

	@JsonProperty("address") val address: ReverseGeocodeAdress,

	@JsonProperty("boundingbox") val boundingBox: List<String>
)

