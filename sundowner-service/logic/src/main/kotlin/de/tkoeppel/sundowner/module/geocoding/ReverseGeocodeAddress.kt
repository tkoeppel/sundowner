package de.tkoeppel.sundowner.module.geocoding

import com.fasterxml.jackson.annotation.JsonProperty

data class ReverseGeocodeAddress(
	@JsonProperty("attraction") val attraction: String?,

	@JsonProperty("house_number") val houseNumber: String?,

	@JsonProperty("road") val road: String?,

	@JsonProperty("neighbourhood") val neighbourhood: String?,

	@JsonProperty("suburb") val suburb: String?,

	@JsonProperty("county") val county: String?,

	@JsonProperty("city") val city: String?,

	@JsonProperty("state") val state: String?,

	@JsonProperty("postcode") val postcode: String?,

	@JsonProperty("country") val country: String?,

	@JsonProperty("country_code") val countryCode: String?
)
