package de.tkoeppel.sundowner.security.securestore.keystore

import de.tkoeppel.sundowner.security.securestore.SecureStoreLoader
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class KeyStoreManager : SecureStoreLoader() {

	@Autowired
	private lateinit var keyStoreConfig: KeyStoreConfig

	@PostConstruct
	private fun loadKeyStore() {
		this.loadKeystore(keyStoreConfig)
	}
}