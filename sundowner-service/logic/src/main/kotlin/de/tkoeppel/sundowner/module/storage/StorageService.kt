package de.tkoeppel.sundowner.module.storage

import de.tkoeppel.sundowner.exceptions.StorageException
import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

@Service
class StorageService(private val minioClient: MinioClient) : IStorageService {
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