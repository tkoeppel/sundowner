package de.tkoeppel.sundowner.module.storage

import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

interface IStorageService {
	fun createBucket(bucketName: String)
	fun listBuckets(): List<String>
	fun bucketExists(bucketName: String): Boolean
	fun uploadFile(bucketName: String, objectName: String, file: MultipartFile)
	fun getObject(bucketName: String, objectName: String): InputStream
	fun removeObject(bucketName: String, objectName: String)
	fun getPresignedObjectUrl(bucketName: String, objectName: String, expiryTime: Int): String
}