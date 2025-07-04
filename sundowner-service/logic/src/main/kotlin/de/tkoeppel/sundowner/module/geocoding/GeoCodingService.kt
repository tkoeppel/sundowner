package de.tkoeppel.sundowner.module.geocoding

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.tkoeppel.sundowner.to.spots.CoordinateTO
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class GeoCodingService(
	private val okHttpClient: OkHttpClient,
	private val objectMapper: ObjectMapper,

	@Value($$"${locationiq.api.key}")
	private val apiKey: String
) {
	private val logger = KotlinLogging.logger {}

	fun reverseGeocode(coordinate: CoordinateTO): ReverseGeocodeResponse? {
		// 3. Build the URL safely and use the input coordinate
		val url = "https://eu1.locationiq.com/v1/reverse".toHttpUrl().newBuilder()
			.addQueryParameter("key", apiKey)
			.addQueryParameter("lat", coordinate.lat.toString())
			.addQueryParameter("lon", coordinate.lng.toString())
			.addQueryParameter("format", "json")
			.build()

		val request = Request.Builder()
			.url(url)
			.get()
			.build()

		try {
			okHttpClient.newCall(request).execute().use { response ->
				if (!response.isSuccessful) {

					return null
				}

				val responseBody = response.body?.string()

				return if (responseBody != null) {
					objectMapper.readValue<ReverseGeocodeResponse>(responseBody)
				} else {
					null
				}
			}
		} catch (e: IOException) {
			return null
		}
	}
}