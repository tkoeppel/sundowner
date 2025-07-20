package de.tkoeppel.sundowner.dao

import de.tkoeppel.sundowner.po.RefreshTokenPO
import de.tkoeppel.sundowner.po.UserPO
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenDAO : JpaRepository<RefreshTokenPO, Long> {
	@Query("SELECT r.user FROM RefreshTokenPO r WHERE r.token = :token")
	fun findUserByToken(token: String): UserPO?

	@Modifying
	@Transactional
	@Query("DELETE FROM RefreshTokenPO r WHERE r.user.username = :username")
	fun removeTokenByUsername(username: String)

	@Modifying
	@Transactional
	@Query("DELETE FROM RefreshTokenPO WHERE expiresAt < CURRENT_TIMESTAMP")
	fun removeExpiredTokens(): Long
}