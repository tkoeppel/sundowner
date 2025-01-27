package de.tkoeppel.sundowner.security.certificate

import java.security.PrivateKey
import java.security.cert.Certificate

data class SundownerCertificate(val alias: String, val certificate: Certificate, val privateKey: PrivateKey)
