package de.tkoeppel.sundowner.exceptions

import de.tkoeppel.sundowner.module.storage.StorageException
import de.tkoeppel.sundowner.security.certificate.InvalidCertificateException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

	@ExceptionHandler(LimitExceededException::class)
	fun handleLimitExceededException(
		exception: LimitExceededException, request: WebRequest
	): ResponseEntity<ErrorDetails> {
		val errorDetails = ErrorDetails(
			timestamp = LocalDateTime.now(), message = exception.message, details = request.getDescription(false)
		)
		return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
	}

	@ExceptionHandler(Exception::class, StorageException::class, InvalidCertificateException::class)
	fun handleGlobalException(
		exception: Exception, request: WebRequest
	): ResponseEntity<ErrorDetails> {
		val errorDetails = ErrorDetails(
			timestamp = LocalDateTime.now(), message = "Internal Server Error", details = request.getDescription(false)
		)
		return ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
	}
}