package de.tkoeppel.sundowner.module.auth

import com.fasterxml.jackson.core.type.TypeReference
import de.tkoeppel.sundowner.security.jwt.JwtService
import de.tkoeppel.sundowner.to.auth.AuthRequestTO
import de.tkoeppel.sundowner.to.auth.AuthResponseTO
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.uuid.ExperimentalUuidApi

class LoginTest : AuthTestBase() {

	@Autowired
	private lateinit var jwtService: JwtService

	@OptIn(ExperimentalUuidApi::class)
	@Test
	@Transactional
	fun `login with valid credentials`() {
		// pre
		val authRequestTO = AuthRequestTO(USERNAME, PASSWORD)

		// act
		val authResponse = loginRequest(authRequestTO)

		// post
		assertThat(authResponse.accessToken).isNotBlank()
		assertThat(authResponse.refreshToken).isNotNull()

		// Verify the access token contains the correct user
		val usernameFromToken = jwtService.extractUsername(authResponse.accessToken)
		assertThat(usernameFromToken).isEqualTo(USERNAME)

		// Verify the refresh token was stored in the database
		val refreshTokenFromDB = refreshTokenDAO.findUserByToken(authResponse.refreshToken)
		assertThat(refreshTokenFromDB).isNotNull
		assertThat(refreshTokenFromDB?.username).isEqualTo(USERNAME)
	}

	@OptIn(ExperimentalUuidApi::class)
	@Test
	@Transactional
	fun `login with valid credentials invalidates previous refresh token`() {
		// pre
		val authRequestTO = AuthRequestTO(USERNAME, PASSWORD)
		val firstAuthResponse = loginRequest(authRequestTO)
		val firstRefreshToken = firstAuthResponse.refreshToken

		// act
		val secondAuthResponse = loginRequest(authRequestTO)
		val secondRefreshToken = secondAuthResponse.refreshToken

		// post
		assertThat(secondRefreshToken).isNotEqualTo(firstRefreshToken)
		assertThat(refreshTokenDAO.findUserByToken(firstRefreshToken)).isNull()

		val newRefreshTokenFromDB = refreshTokenDAO.findUserByToken(secondRefreshToken)
		assertThat(newRefreshTokenFromDB).isNotNull
		assertThat(newRefreshTokenFromDB?.username).isEqualTo(USERNAME)
	}

	@Test
	fun `login with invalid password`() {
		// pre
		val authRequestTO = AuthRequestTO(USERNAME, "wrong_password")

		// act & post
		this.mockMvc.perform(
			buildLoginRequest(authRequestTO)
		).andExpect(status().isUnauthorized)
	}

	@Test
	fun `login with non-existent user`() {
		// pre
		val authRequestTO = AuthRequestTO("non-existent-user", "any-password")

		// act & post
		this.mockMvc.perform(
			buildLoginRequest(authRequestTO)
		).andExpect(status().isUnauthorized)
	}

	private fun buildLoginRequest(authRequestTO: AuthRequestTO): MockHttpServletRequestBuilder {
		return post(LOGIN_PATH).contentType(MediaType.APPLICATION_JSON)
			.content(this.mapper.writeValueAsString(authRequestTO))
	}

	private fun loginRequest(authRequestTO: AuthRequestTO): AuthResponseTO {
		val result = this.mockMvc.perform(
			buildLoginRequest(authRequestTO)
		).andExpect(status().isOk).andReturn()

		return this.convertToTO(result, object : TypeReference<AuthResponseTO>() {})
	}
}