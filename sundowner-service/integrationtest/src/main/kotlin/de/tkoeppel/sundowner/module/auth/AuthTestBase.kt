package de.tkoeppel.sundowner.module.auth

import de.tkoeppel.sundowner.SundownerServiceTestBase
import de.tkoeppel.sundowner.po.RefreshTokenPO
import de.tkoeppel.sundowner.po.UserPO
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AuthTestBase : SundownerServiceTestBase() {
	protected val AUTH_PATH = "$API_PATH/auth"

	protected val LOGIN_PATH = "$AUTH_PATH/login"

	protected val LOGOUT_PATH = "$AUTH_PATH/logout"

	companion object {
		protected const val USERNAME = "user"
		protected const val PASSWORD = "user_password"
	}

	@OptIn(ExperimentalUuidApi::class)
	protected fun createRefreshToken(
		token: Uuid = Uuid.random(),
		user: UserPO = this.user,
		expiresAt: ZonedDateTime = ZonedDateTime.now().plusDays(1),
		createdAt: ZonedDateTime = ZonedDateTime.now()
	): RefreshTokenPO {
		val po = RefreshTokenPO(
			token, user, expiresAt, createdAt
		)
		this.refreshTokenDAO.save(po)
		return po
	}

}