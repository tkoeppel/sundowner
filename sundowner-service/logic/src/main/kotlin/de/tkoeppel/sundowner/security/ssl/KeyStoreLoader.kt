package de.tkoeppel.sundowner.security.ssl


import okhttp3.OkHttpClient
import java.io.File
import java.io.FileInputStream
import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


class KeyStoreLoader {
	companion object {
		fun createHttpClient(ssl: SslData): OkHttpClient {
			// Load the keystore
			val keyStore = KeyStore.getInstance("PKCS12")
			keyStore.load(FileInputStream(File(ssl.keystorePath)), ssl.keystorePassword.toCharArray())

			// Get the key manager factory
			val kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
			kmf.init(keyStore, ssl.keyPassword?.toCharArray())

			// Get the trust manager factory (for trusting certificates)
			val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
			tmf.init(keyStore)

			// Create an SSL context
			val sslContext = SSLContext.getInstance("TLS")
			sslContext.init(kmf.keyManagers, tmf.trustManagers, null)

			// Get the X509TrustManager (for trusting certificates)
			val trustManager = tmf.trustManagers.first { it is X509TrustManager } as X509TrustManager

			// Create the OkHttpClient
			return OkHttpClient.Builder().sslSocketFactory(sslContext.socketFactory, trustManager).build()
		}
	}
}