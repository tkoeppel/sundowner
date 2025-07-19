package de.tkoeppel.sundowner.module.auth

import de.tkoeppel.sundowner.dao.RefreshTokenDAO
import de.tkoeppel.sundowner.dao.UserDAO
import de.tkoeppel.sundowner.module.users.SundownerUser
import de.tkoeppel.sundowner.module.users.SundownerUserDetailsService
import de.tkoeppel.sundowner.po.RefreshTokenPO
import de.tkoeppel.sundowner.security.jwt.JwtConfig
import de.tkoeppel.sundowner.security.jwt.JwtService
import de.tkoeppel.sundowner.to.auth.AuthRequestTO
import de.tkoeppel.sundowner.to.auth.AuthResponseTO
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Service
class AuthService(
	private val authManager: AuthenticationManager,
	private val userDetailsService: SundownerUserDetailsService,
	private val jwtService: JwtService,
	private val refreshTokenDAO: RefreshTokenDAO,
	private val userDAO: UserDAO,
	private val jwtConfig: JwtConfig
) {
	fun login(authenticationRequest: AuthRequestTO): AuthResponseTO {

		val authRequest = UsernamePasswordAuthenticationToken(
			authenticationRequest.username, authenticationRequest.password
		)
		authManager.authenticate(authRequest)

		val user = userDetailsService.loadUserByUsername(authenticationRequest.username)

		val accessToken = createAccessToken(user)
		val refreshToken = createRefreshToken(user)

		return AuthResponseTO(
			accessToken, refreshToken
		)
	}

	fun logout(refreshToken: String) {
		this.refreshTokenDAO.deleteByToken(refreshToken)
	}

	fun refreshAccessToken(refreshToken: String): String {
		val username = jwtService.extractUsername(refreshToken)

		return username.let { user ->
			val currentUserDetails = userDetailsService.loadUserByUsername(user)
			val refreshTokenUserDetails = this.refreshTokenDAO.findUserByToken(refreshToken)

			if (currentUserDetails.username == refreshTokenUserDetails?.username) {
				createAccessToken(currentUserDetails)
			} else {
				throw AuthenticationServiceException("Invalid refresh token")
			}
		}
	}

	private fun createAccessToken(
		user: SundownerUser, additionalClaims: Map<String, Any> = emptyMap()
	): String {
		val expiresAt = Date(System.currentTimeMillis() + jwtConfig.accessTokenExpiration)

		return jwtService.generateToken(
			user.username, expiresAt, additionalClaims
		)
	}

	private fun createRefreshToken(
		user: SundownerUser, additionalClaims: Map<String, Any> = emptyMap()
	): String {
		val expiresAt = Date(System.currentTimeMillis() + jwtConfig.refreshTokenExpiration)
		val token = jwtService.generateToken(
			user.username, expiresAt, additionalClaims
		)
		val userRef = this.userDAO.getReferenceById(user.id)
		val expiresAtZoned = expiresAt.toInstant().atZone(ZoneId.systemDefault())

		// create PO
		val refreshTokenPO = RefreshTokenPO(token, userRef, expiresAtZoned, ZonedDateTime.now())
		this.refreshTokenDAO.save(refreshTokenPO)

		return token
	}
}