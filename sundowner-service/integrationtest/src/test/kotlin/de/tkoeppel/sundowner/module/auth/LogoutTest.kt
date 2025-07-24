package de.tkoeppel.sundowner.module.auth

import com.fasterxml.jackson.core.type.TypeReference
import de.tkoeppel.sundowner.to.auth.AuthRequestTO
import de.tkoeppel.sundowner.to.auth.AuthResponseTO
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.uuid.ExperimentalUuidApi

class LogoutTest : AuthTestBase() {

	@OptIn(ExperimentalUuidApi::class)
	@Test
	@Transactional
	fun `logout with valid token successfully deletes refresh token`() {
		// pre
		val authRequest = AuthRequestTO(USERNAME, PASSWORD)
		val authResponse = loginRequest(authRequest)
		val refreshToken = authResponse.refreshToken
		val accessToken = authResponse.accessToken

		val refreshTokens = refreshTokenDAO.findAll()
		assertThat(refreshTokens).hasSize(1)
		assertThat(refreshTokens[0].token).isEqualTo(refreshToken)
		assertThat(refreshTokenDAO.findUserByToken(refreshToken)).isNotNull

		// act
		this.mockMvc.perform(
			post(LOGOUT_PATH).header("Authorization", "Bearer $accessToken")
		).andExpect(status().isOk)

		// post
		assertThat(refreshTokenDAO.findUserByToken(refreshToken)).isNull()
	}

	@Test
	fun `logout without authentication token`() {
		this.mockMvc.perform(
			post(LOGOUT_PATH)
		).andExpect(status().isUnauthorized)
	}


	private fun loginRequest(authRequestTO: AuthRequestTO): AuthResponseTO {
		val result = this.mockMvc.perform(
			post(LOGIN_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(authRequestTO))
		).andExpect(status().isOk).andReturn()

		return this.convertToTO(result, object : TypeReference<AuthResponseTO>() {})
	}
}