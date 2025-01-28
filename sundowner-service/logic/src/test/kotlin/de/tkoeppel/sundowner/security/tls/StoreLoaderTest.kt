package de.tkoeppel.sundowner.security.tls

import de.tkoeppel.sundowner.security.certificate.SundownerCertificate
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.util.logging.Logger

@ExtendWith(MockitoExtension::class)
class StoreLoaderTest {

	@Test
	fun `test loading the store with an empty config`() {
		// pre
		val certificates = mapOf<String, SundownerCertificate>()
		val logger = mock(Logger::class.java)
		val config = TestStoreConfig("", "", "", mapOf())
		val storeLoader = TestStoreManager(logger, null, certificates)

		// act
		storeLoader.initStore(config)

		// post
		verify(logger).info("No teststore configuration provided.")
		assertThrows<IllegalStateException> { storeLoader.getStore() }
	}


}