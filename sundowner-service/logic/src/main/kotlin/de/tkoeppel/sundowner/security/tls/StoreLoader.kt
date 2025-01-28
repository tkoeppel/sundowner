package de.tkoeppel.sundowner.security.tls

import de.tkoeppel.sundowner.security.certificate.InvalidCertificateException
import de.tkoeppel.sundowner.security.certificate.SundownerCertificate
import org.springframework.core.io.ClassPathResource
import java.security.KeyStore
import java.security.PrivateKey
import java.security.cert.Certificate
import java.util.logging.Logger


abstract class StoreLoader() {
	private val logger = Logger.getLogger(StoreLoader::class.java.name)
	private lateinit var secureStore: KeyStore
	private lateinit var certificates: Map<String, SundownerCertificate>

	fun loadKeystore(config: StoreConfig) {
		val keyStore = KeyStore.getInstance(config.type)
		val resource = ClassPathResource(config.path)
		val keystoreStream = resource.getInputStream()
		keyStore.load(keystoreStream, config.password.toCharArray())
		this.secureStore = keyStore
		this.certificates = config.keys.map { (alias, passphrase) ->
			if (passphrase.isEmpty()) {
				logger.info("No passphrase provided for alias $alias. Keystore password will be used.")
				loadCertificate(alias, config.password)
			} else {
				loadCertificate(alias, passphrase)
			}
		}.associateBy { it.alias }
	}

	private fun loadCertificate(alias: String, password: String): SundownerCertificate {
		return SundownerCertificate(
			alias,
			this.secureStore.getCertificate(alias) as Certificate,
			this.secureStore.getKey(alias, password.toCharArray()) as PrivateKey
		)
	}

	fun getCertificate(alias: String): SundownerCertificate {
		if (!this.certificates.contains(alias)) {
			throw InvalidCertificateException("Certificate with alias $alias not found.")
		}

		return this.certificates[alias]!!
	}

	fun hasCertificate(alias: String): Boolean {
		return this.certificates.contains(alias)
	}

}
