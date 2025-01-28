package de.tkoeppel.sundowner.security.tls.truststore

import de.tkoeppel.sundowner.security.tls.StoreLoader
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TrustStoreManager : StoreLoader() {

	@Autowired
	private lateinit var trustStoreConfig: TrustStoreConfig

	@PostConstruct
	private fun loadTrustStore() {
		this.initStore(trustStoreConfig)
	}

	override fun getName(): String {
		return "trust"
	}
}