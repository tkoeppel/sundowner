package de.tkoeppel.sundowner.configuration

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class HttpClientConfiguration {
	
	@Bean
	fun httpClient(): OkHttpClient {
		return OkHttpClient()
	}
}