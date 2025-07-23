package de.tkoeppel.sundowner.security.jwt

import de.tkoeppel.sundowner.dao.RefreshTokenDAO
import io.ktor.util.logging.*
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RefreshTokenScheduler(val refreshTokenDAO: RefreshTokenDAO) {

	companion object {
		private val LOGGER: Logger = LoggerFactory.getLogger(RefreshTokenScheduler::class.java)
	}

	@Scheduled(cron = $$"${security.jwt.remove-expired-token-interval:0 0 * * * *}")
	fun scheduledRemoveExpired() {
		val tokensDeleted = this.refreshTokenDAO.deleteExpiredTokens()
		LOGGER.info("Removed $tokensDeleted expired refresh tokens")
	}
}