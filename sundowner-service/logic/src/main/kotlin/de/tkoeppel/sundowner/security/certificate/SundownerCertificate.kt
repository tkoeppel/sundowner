package de.tkoeppel.sundowner.security.certificate

import java.security.PrivateKey
import java.security.cert.Certificate
import java.security.cert.X509Certificate
import java.time.ZonedDateTime

data class SundownerCertificate(val alias: String, val certificate: Certificate, val privateKey: PrivateKey) {
	fun checkValid() {
		(this.certificate as X509Certificate).checkValidity()
	}

	fun willExpireSoon(days: Long): Boolean {
		val cert = this.certificate as X509Certificate
		val futureDate = ZonedDateTime.now().plusDays(days)
		return cert.notAfter.toInstant().isBefore(futureDate.toInstant())
	}
}
