package de.tkoeppel.sundowner.security.tls

import de.tkoeppel.sundowner.security.certificate.SundownerCertificate
import java.security.KeyStore
import java.util.logging.Logger

class TestStoreManager(
	logger: Logger, store: KeyStore?, certificates: Map<String, SundownerCertificate>
) : StoreLoader(logger, store, certificates) {
	override fun getName(): String {
		return "test"
	}
}