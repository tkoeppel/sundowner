package de.tkoeppel.sundowner.security.certificate

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.security.PrivateKey
import java.security.cert.CertificateExpiredException
import java.security.cert.X509Certificate
import java.time.ZonedDateTime
import java.util.*
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class SundownerCertificateTest {
	companion object {
		private val NOW = ZonedDateTime.now()
		private val ALIAS = "sundowner-alias"
	}

	private val mockPrivateKey = mock(PrivateKey::class.java)
	private val mockCertificate = mock(X509Certificate::class.java)

	@Test
	fun `checkValid should not throw exception when certificate is valid`() {
		// pre
		val sundownerCertificate = SundownerCertificate(ALIAS, mockCertificate, mockPrivateKey)

		// act
		sundownerCertificate.checkValid()

		// post
		verify(mockCertificate).checkValidity()
	}

	@Test
	fun `checkValid should throw exception when certificate is invalid`() {
		// pre
		`when`(mockCertificate.checkValidity()).thenThrow(CertificateExpiredException())
		val sundownerCertificate = SundownerCertificate(ALIAS, mockCertificate, mockPrivateKey)

		// act / post
		assertThrows<CertificateExpiredException> {
			sundownerCertificate.checkValid()
		}
	}

	@Test
	fun `willExpireSoon should return false when certificate is valid for given days`() {
		// pre
		val futureDate = NOW.plusDays(31)
		`when`(mockCertificate.notAfter).thenReturn(Date.from(futureDate.toInstant()))
		val sundownerCertificate = SundownerCertificate(ALIAS, mockCertificate, mockPrivateKey)
		val days = 30L

		// act
		val result = sundownerCertificate.willExpireSoon(days)

		// post
		assertFalse(result)
	}

	@Test
	fun `willExpireSoon should return true when certificate will expire within given days`() {
		// pre
		val futureDate = NOW.plusDays(30)
		`when`(mockCertificate.notAfter).thenReturn(Date.from(futureDate.toInstant()))
		val sundownerCertificate = SundownerCertificate(ALIAS, mockCertificate, mockPrivateKey)
		val days = 30L

		// act
		val result = sundownerCertificate.willExpireSoon(days)

		// post
		assertTrue(result)
	}


	@Test
	fun `willExpireSoon returns true when certificate expires exactly on given day`() {
		// pre
		val futureDate = NOW.plusDays(30)
		`when`(mockCertificate.notAfter).thenReturn(Date.from(futureDate.toInstant()))
		val sundownerCertificate = SundownerCertificate(ALIAS, mockCertificate, mockPrivateKey)
		val days = 30L

		// act
		val result = sundownerCertificate.willExpireSoon(days)

		// post
		assertTrue(result)
	}


	@Test
	fun `willExpireSoon returns true when certificate is already expired`() {
		// pre
		val pastDate = NOW.minusDays(1) // Already expired
		`when`(mockCertificate.notAfter).thenReturn(Date.from(pastDate.toInstant()))
		val sundownerCertificate = SundownerCertificate(ALIAS, mockCertificate, mockPrivateKey)
		val days = 30L

		// act
		val result = sundownerCertificate.willExpireSoon(days)

		// post
		assertTrue(result)
	}


	@Test
	fun `willExpireSoon handles negative days correctly`() {
		// pre
		val futureDate = NOW.plusDays(30)
		`when`(mockCertificate.notAfter).thenReturn(Date.from(futureDate.toInstant()))
		val sundownerCertificate = SundownerCertificate(ALIAS, mockCertificate, mockPrivateKey)
		val days = -30L // Negative days

		// act
		val result = sundownerCertificate.willExpireSoon(days)

		// post
		assertFalse(result)
	}
}