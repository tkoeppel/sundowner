package de.tkoeppel.sundowner.dao

import de.tkoeppel.sundowner.po.RefreshTokenPO
import de.tkoeppel.sundowner.po.UserPO
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface RefreshTokenDAO : GeneralDAO<RefreshTokenPO> {
	@OptIn(ExperimentalUuidApi::class)
	@Query("SELECT r.user FROM RefreshTokenPO r WHERE r.token = :token")
	fun findUserByToken(token: Uuid): UserPO?

	@Modifying
	@Transactional
	@Query("DELETE FROM RefreshTokenPO WHERE expiresAt < CURRENT_TIMESTAMP")
	fun deleteExpiredTokens(): Int

	@Modifying
	@Transactional
	@Query("DELETE FROM RefreshTokenPO r WHERE r.user.username = :username")
	fun deleteTokenByUsername(username: String)
}