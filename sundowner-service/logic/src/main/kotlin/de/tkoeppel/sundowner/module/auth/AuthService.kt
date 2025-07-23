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
import jakarta.transaction.Transactional
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Service
class AuthService(
	private val sundownerAuthProvider: SundownerAuthProvider,
	private val userDetailsService: SundownerUserDetailsService,
	private val jwtService: JwtService,
	private val refreshTokenDAO: RefreshTokenDAO,
	private val userDAO: UserDAO,
	private val jwtConfig: JwtConfig
) {
	@OptIn(ExperimentalUuidApi::class)
	@Transactional
	fun login(authenticationRequest: AuthRequestTO): AuthResponseTO {

		val authRequest = UsernamePasswordAuthenticationToken(
			authenticationRequest.username, authenticationRequest.password
		)
		val authentication = sundownerAuthProvider.authenticate(authRequest)

		val user = authentication.principal as SundownerUser

		val accessToken = createAccessToken(user)
		val refreshToken = createRefreshToken(user)

		return AuthResponseTO(
			accessToken, refreshToken
		)
	}

	@OptIn(ExperimentalUuidApi::class)
	@Transactional
	fun logout(refreshToken: Uuid) {
		this.refreshTokenDAO.deleteByToken(refreshToken)
	}

	@OptIn(ExperimentalUuidApi::class)
	@Transactional
	fun refreshAccessToken(refreshToken: Uuid): String {
		val userPO = this.refreshTokenDAO.findUserByToken(refreshToken)

		return userPO.let { user ->
			val currentUserDetails = userDetailsService.loadUserByUserPO(user)
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


	@OptIn(ExperimentalUuidApi::class)
	private fun createRefreshToken(
		user: SundownerUser
	): Uuid {
		this.refreshTokenDAO.removeTokenByUsername(user.username)

		val expiresAt = Date(System.currentTimeMillis() + jwtConfig.refreshTokenExpiration)
		val token = Uuid.random()
		val userRef = this.userDAO.getReferenceById(user.id)
		val expiresAtZoned = expiresAt.toInstant().atZone(ZoneId.systemDefault())

		// create PO
		val refreshTokenPO = RefreshTokenPO(token, userRef, expiresAtZoned, ZonedDateTime.now())
		this.refreshTokenDAO.save(refreshTokenPO)

		return token
	}
}