package de.tkoeppel.sundowner.security.web

import de.tkoeppel.sundowner.module.users.SundownerUserDetailsService
import de.tkoeppel.sundowner.security.jwt.JwtAuthFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
class WebSecurityConfig(
	private val sundownerUserDetailsService: SundownerUserDetailsService, //
	private val jwtAuthFilter: JwtAuthFilter
) {
	@Bean
	fun passwordEncoder(): PasswordEncoder {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder()
	}

	@Bean
	fun authenticationManager(
		userDetailsService: UserDetailsService?, passwordEncoder: PasswordEncoder?
	): AuthenticationManager {
		val authenticationProvider = DaoAuthenticationProvider(userDetailsService)
		authenticationProvider.setPasswordEncoder(passwordEncoder)

		return ProviderManager(authenticationProvider)
	}


	@Bean
	fun filterChain(http: HttpSecurity): SecurityFilterChain {
		http //
			.csrf(AbstractHttpConfigurer<*, *>::disable) //
			.cors(Customizer.withDefaults()) //
			.authenticationManager { authentication ->
				authenticationManager(
					this.sundownerUserDetailsService, passwordEncoder()
				).authenticate(authentication)
			}.authorizeHttpRequests {
				it //
					.requestMatchers("/api/v1/*/public/**", "/api/v1/api-docs") //
					.permitAll() //
					.anyRequest() //
					.authenticated()
			}.addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java) //
			.sessionManagement {
				it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			}
		return http.build()
	}
}