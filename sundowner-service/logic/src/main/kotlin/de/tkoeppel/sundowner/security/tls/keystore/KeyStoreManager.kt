package de.tkoeppel.sundowner.security.tls.keystore

import de.tkoeppel.sundowner.security.tls.StoreLoader
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class KeyStoreManager : StoreLoader() {

	@Autowired
	private lateinit var keyStoreConfig: KeyStoreConfig

	@PostConstruct
	private fun loadKeyStore() {
		this.loadKeystore(keyStoreConfig)
	}
}