package de.tkoeppel.sundowner.security.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
class WebSecurityConfig {

	@Bean
	fun passwordEncoder(): PasswordEncoder {
		return BCryptPasswordEncoder()
	}

	@Bean
	fun filterChain(http: HttpSecurity): SecurityFilterChain {
		http.authorizeHttpRequests { authorize ->
			authorize.requestMatchers("/api/v1/*/public/**").permitAll() //
				.anyRequest().authenticated()
		}.formLogin { } //
			.cors(Customizer.withDefaults())
		return http.build()
	}
}