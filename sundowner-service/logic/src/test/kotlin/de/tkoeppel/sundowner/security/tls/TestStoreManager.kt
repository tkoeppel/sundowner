package de.tkoeppel.sundowner.security.tls

import java.security.KeyStore
import java.util.logging.Logger

class TestStoreManager(
	logger: Logger, store: KeyStore?
) : StoreLoader(logger, store, mapOf()) {
	override fun getName(): String {
		return "test"
	}
}