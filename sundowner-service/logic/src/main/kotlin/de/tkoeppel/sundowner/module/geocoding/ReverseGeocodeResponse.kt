package de.tkoeppel.sundowner.module.geocoding

import com.fasterxml.jackson.annotation.JsonProperty

data class ReverseGeocodeResponse(
	@JsonProperty("place_id")
	val placeId: String,

	@JsonProperty("licence")
	val licence: String,

	@JsonProperty("osm_type")
	val osmType: String,

	@JsonProperty("osm_id")
	val osmId: String,

	@JsonProperty("lat")
	val lat: String,

	@JsonProperty("lon")
	val lon: String,

	@JsonProperty("display_name")
	val displayName: String,

	@JsonProperty("address")
	val address: Address,

	@JsonProperty("boundingbox")
	val boundingBox: List<String>
)

data class Address(
	@JsonProperty("attraction")
	val attraction: String?,

	@JsonProperty("house_number")
	val houseNumber: String?,

	@JsonProperty("road")
	val road: String?,

	@JsonProperty("neighbourhood")
	val neighbourhood: String?,

	@JsonProperty("suburb")
	val suburb: String?,

	@JsonProperty("county")
	val county: String?,

	@JsonProperty("city")
	val city: String?,

	@JsonProperty("state")
	val state: String?,

	@JsonProperty("postcode")
	val postcode: String?,

	@JsonProperty("country")
	val country: String?,

	@JsonProperty("country_code")
	val countryCode: String?
)