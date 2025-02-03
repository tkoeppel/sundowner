package de.tkoeppel.sundowner.security.tls

import de.tkoeppel.sundowner.security.certificate.InvalidCertificateException
import io.ktor.network.tls.certificates.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.core.io.ClassPathResource
import java.io.File
import java.security.KeyStore
import java.util.logging.Logger
import java.util.stream.Stream


class StoreLoaderTest {
	companion object {
		const val FILENAME = "teststore.pfx"
		const val TYPE = "PKCS12"
		const val ALIAS = "sundowner"
		const val PASSWORD = "pwdpwd"

		lateinit var storeFile: File
		lateinit var testStore: KeyStore
		lateinit var defaultConfig: TestStoreConfig

		@BeforeAll
		@JvmStatic
		fun setup() {
			val resource = ClassPathResource(FILENAME)
			storeFile = resource.file
			testStore = buildKeyStore {
				certificate(ALIAS) {
					password = PASSWORD
					domains = listOf("localhost")
				}
			}
			testStore.saveToFile(storeFile, PASSWORD)
			defaultConfig = TestStoreConfig(TYPE, storeFile.absolutePath, PASSWORD, mapOf(ALIAS to ""))
		}

		@JvmStatic
		fun invalidStoreConfigSource(): Stream<TestStoreConfig> {
			return Stream.of(
				TestStoreConfig("invalid", storeFile.absolutePath, PASSWORD, mapOf(ALIAS to "")),
				TestStoreConfig(TYPE, "/into/nirvana", PASSWORD, mapOf(ALIAS to "")),
				TestStoreConfig(TYPE, storeFile.absolutePath, "123456", mapOf(ALIAS to "")),
			)
		}
	}

	private var logger: Logger = mock(Logger::class.java)
	private var storeLoader = TestStoreManager(logger, testStore)

	@Test
	fun `test loading the store with an empty config`() {
		// pre
		val config = TestStoreConfig("", "", "", mapOf())

		// act
		storeLoader.initStore(config)

		// post
		verify(logger).info("No teststore configuration provided.")
	}

	@Test
	fun `test loading the store successfully`() {
		// act
		storeLoader.initStore(defaultConfig)

		// post
		verify(logger).info("Initialized teststore successfully.")
		verify(logger).info("Initialized teststore with 1 certificates.")
		assertThat(storeLoader.getStore()).isNotNull()
		assertThat(storeLoader.hasCertificate(ALIAS)).isTrue()
	}

	@MethodSource("invalidStoreConfigSource")
	@ParameterizedTest
	fun `test loading the store with invalid store config`(config: TestStoreConfig) {
		// act
		val error = assertThrows<TlsException> { storeLoader.initStore(config) }

		// post
		assertThat(error.message).isEqualTo("Failed to load teststore.")
	}

	@CsvSource("$ALIAS,blabla", "foo,$PASSWORD")
	@ParameterizedTest
	fun `test loading certificates with invalid certificate details`(alias: String, password: String) {
		// pre
		val config = TestStoreConfig(TYPE, storeFile.absolutePath, PASSWORD, mapOf(alias to password))

		// act
		val error = assertThrows<TlsException> { storeLoader.initStore(config) }

		// post
		assertThat(error.message).isEqualTo("Failed to load teststore certificates.")
	}

	@Test
	fun `test get certificate with invalid alias`() {
		// act
		storeLoader.initStore(defaultConfig)
		val error = assertThrows<InvalidCertificateException> { storeLoader.getCertificate("blabla") }

		// post
		assertThat(error.message).isEqualTo("Certificate with alias blabla not found.")
	}

	@Test
	fun `test get not loaded store`() {
		// pre
		storeLoader = TestStoreManager(logger, null)

		// act
		val error = assertThrows<TlsException> { storeLoader.getStore() }

		// post
		assertThat(error.message).isEqualTo("The teststore is not loaded.")
	}


}
