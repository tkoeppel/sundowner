package de.tkoeppel.sundowner.module.geocoding

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.tkoeppel.sundowner.to.spots.CoordinateTO
import io.github.oshai.kotlinlogging.KotlinLogging
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class GeoCodingService(
	private val okHttpClient: OkHttpClient, //
	private val objectMapper: ObjectMapper,

	@Value($$"${locationiq.api.key}") private val apiKey: String,

	@Value($$"${locationiq.api.url}") private val apiUrl: String
) {
	private val logger = KotlinLogging.logger {}

	fun reverseGeocode(coordinate: CoordinateTO): ReverseGeoCodeDetails? {

		val url = "${this.apiUrl}/reverse".toHttpUrl().newBuilder().addQueryParameter("key", apiKey)
			.addQueryParameter("lat", coordinate.lat.toString()) //
			.addQueryParameter("lon", coordinate.lng.toString()) //
			.addQueryParameter("format", "json").build()

		val request = Request.Builder().url(url).get().build()

		try {
			okHttpClient.newCall(request).execute().use { response ->
				if (!response.isSuccessful) {
					logger.error { "Request to LocationIQ failed with status code ${response.code} and message ${response.message}" }
					throw GeoCodingException("Request to LocationIQ failed with status code ${response.code}")
				}

				val responseBody = response.body?.string()

				return if (responseBody != null) {
					objectMapper.readValue<ReverseGeoCodeDetails>(responseBody)
				} else {
					logger.error { "No valid reverse geocode details found" }
					throw GeoCodingException("No valid reverse geocode details found")
				}
			}
		} catch (e: IOException) {
			logger.error { "Request to LocationIQ failed: ${e.message}" }
			throw GeoCodingException("Request to LocationIQ failed: ${e.message}")
		}
	}
}