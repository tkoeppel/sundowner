package de.tkoeppel.sundowner.security.certificate

import java.security.PrivateKey
import java.security.cert.Certificate
import java.security.cert.CertificateExpiredException
import java.security.cert.X509Certificate
import java.time.ZonedDateTime
import java.util.*

data class SundownerCertificate(val alias: String, val certificate: Certificate, val privateKey: PrivateKey) {
	fun checkValid() {
		(this.certificate as X509Certificate).checkValidity()
	}

	fun willExpireSoon(days: Long): Boolean {
		val futureDate = ZonedDateTime.now().plusDays(days).toInstant()
		return try {
			(this.certificate as X509Certificate).checkValidity(Date.from(futureDate)); false
		} catch (_: CertificateExpiredException) {
			true
		}
	}

}
