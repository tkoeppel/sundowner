package de.tkoeppel.sundowner.security.tls

import de.tkoeppel.sundowner.security.certificate.InvalidCertificateException
import de.tkoeppel.sundowner.security.certificate.SundownerCertificate
import org.springframework.core.io.ClassPathResource
import java.security.KeyStore
import java.security.PrivateKey
import java.security.cert.Certificate
import java.util.logging.Logger


abstract class StoreLoader(
	private val logger: Logger = Logger.getLogger(StoreLoader::class.java.name),
	private var store: KeyStore? = null,
	private var certificates: Map<String, SundownerCertificate> = mapOf()
) {

	fun initStore(config: StoreConfig) {
		if (config.type.isEmpty() || config.path.isEmpty() || config.password.isEmpty()) {
			this.logger.info("No ${getName()}store configuration provided.")
			return
		}

		this.store = loadStore(config)
		this.certificates = loadCertificates(config)
	}

	private fun loadStore(config: StoreConfig): KeyStore {
		val keyStore = KeyStore.getInstance(config.type)
		val resource = ClassPathResource(config.path)
		val keystoreStream = resource.getInputStream()
		keyStore.load(keystoreStream, config.password.toCharArray())
		return keyStore
	}

	private fun loadCertificates(config: StoreConfig): Map<String, SundownerCertificate> {
		return config.keys.map { (alias, passphrase) ->
			if (passphrase.isEmpty()) {
				logger.info("No passphrase provided for alias $alias. The ${getName()}store password will be used.")
				loadCertificate(alias, config.password)
			} else {
				loadCertificate(alias, passphrase)
			}
		}.associateBy { it.alias }
	}

	private fun loadCertificate(alias: String, password: String): SundownerCertificate {
		return SundownerCertificate(
			alias,
			this.store!!.getCertificate(alias) as Certificate,
			this.store!!.getKey(alias, password.toCharArray()) as PrivateKey
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

	fun getStore(): KeyStore {
		if (this.store == null) throw IllegalStateException("The ${getName()}store is not loaded.")
		return this.store!!
	}

	abstract fun getName(): String

}
