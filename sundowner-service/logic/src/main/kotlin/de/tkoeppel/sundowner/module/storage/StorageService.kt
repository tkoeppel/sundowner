package de.tkoeppel.sundowner.module.storage

import de.tkoeppel.sundowner.security.certificate.InvalidCertificateException
import de.tkoeppel.sundowner.security.tls.truststore.TrustStoreManager
import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import jakarta.annotation.PostConstruct
import okhttp3.OkHttpClient
import org.apache.hc.core5.ssl.SSLContexts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


@Service
class StorageService() : IStorageService {

	private val logger: java.util.logging.Logger = java.util.logging.Logger.getLogger(StorageService::class.java.name)

	@Autowired
	private lateinit var storageConfig: StorageConfig

	@Autowired
	private lateinit var trustStoreManager: TrustStoreManager

	private lateinit var minioClient: MinioClient

	@PostConstruct
	fun initMinioClient() {
		if (storageConfig.endpoint.isEmpty() || storageConfig.accessKey.isEmpty() || storageConfig.secretKey.isEmpty()) {
			this.logger.warning { "No storage complete configuration provided." }
			return
		}

		val builder = MinioClient.builder() //
			.endpoint(storageConfig.endpoint) //
			.credentials(storageConfig.accessKey, storageConfig.secretKey) //

		this.minioClient =
			if (storageConfig.trustStoreAlias.isEmpty()) builder.build() else builder.httpClient(createHttpClient())
				.build()
	}

	private fun createHttpClient(): OkHttpClient {
		if (!this.trustStoreManager.hasCertificate(storageConfig.trustStoreAlias!!)) {
			throw InvalidCertificateException("Certificate with alias ${storageConfig.trustStoreAlias} not found.")
		}

		val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
		trustManagerFactory.init(this.trustStoreManager.getStore())
		val trustManagers = trustManagerFactory.trustManagers
		check(trustManagers.size == 1 && trustManagers[0] is X509TrustManager) {
			"Unexpected default trust managers: ${trustManagers.contentToString()}"
		}
		val trustManager = trustManagers[0] as X509TrustManager
		val sslContext = SSLContexts.custom().loadTrustMaterial(this.trustStoreManager.getStore(), null).build()

		return OkHttpClient.Builder().sslSocketFactory(sslContext.socketFactory, trustManager).build()
	}

	override fun createBucket(bucketName: String) {
		try {
			if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
				minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build())
			}
		} catch (e: Exception) {
			throw StorageException("Error creating bucket: $bucketName", e)
		}
	}

	override fun listBuckets(): List<String> {
		TODO("Not yet implemented")
	}

	override fun bucketExists(bucketName: String): Boolean {
		TODO("Not yet implemented")
	}

	override fun uploadFile(
		bucketName: String, objectName: String, file: MultipartFile
	) {
		TODO("Not yet implemented")
	}

	override fun getObject(bucketName: String, objectName: String): InputStream {
		TODO("Not yet implemented")
	}

	override fun removeObject(bucketName: String, objectName: String) {
		TODO("Not yet implemented")
	}

	override fun getPresignedObjectUrl(
		bucketName: String, objectName: String, expiryTime: Int
	): String {
		TODO("Not yet implemented")
	}

}
