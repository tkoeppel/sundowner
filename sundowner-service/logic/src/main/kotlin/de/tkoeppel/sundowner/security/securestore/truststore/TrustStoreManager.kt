package de.tkoeppel.sundowner.security.securestore.truststore

import de.tkoeppel.sundowner.security.securestore.SecureStoreLoader
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TrustStoreManager : SecureStoreLoader() {

	@Autowired
	private lateinit var trustStoreConfig: TrustStoreConfig

	@PostConstruct
	private fun loadTrustStore() {
		this.loadKeystore(trustStoreConfig)
	}
}