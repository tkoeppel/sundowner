package de.tkoeppel.sundowner.security.jwt

import de.tkoeppel.sundowner.dao.RefreshTokenDAO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*


class RefreshTokenSchedulerTest {
	private var refreshTokenDAO: RefreshTokenDAO? = null
	private var refreshTokenScheduler: RefreshTokenScheduler? = null

	@BeforeEach
	fun createScheduler() {
		this.refreshTokenDAO = mock(RefreshTokenDAO::class.java)
		this.refreshTokenScheduler = RefreshTokenScheduler(this.refreshTokenDAO!!)
	}

	@Test
	fun `scheduledRemoveExpired should call dao to remove expired tokens`() {
		// pre
		val expectedDeletedCount = 5L
		`when`(refreshTokenDAO!!.removeExpiredTokens()).thenReturn(expectedDeletedCount)

		// act
		this.refreshTokenScheduler!!.scheduledRemoveExpired()

		//post
		verify(refreshTokenDAO, times(1))!!.removeExpiredTokens()
	}
}