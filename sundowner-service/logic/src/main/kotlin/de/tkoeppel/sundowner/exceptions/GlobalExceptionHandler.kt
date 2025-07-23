package de.tkoeppel.sundowner.exceptions

import de.tkoeppel.sundowner.module.spots.InvalidSpotInputException
import de.tkoeppel.sundowner.module.storage.StorageException
import de.tkoeppel.sundowner.security.certificate.InvalidCertificateException
import de.tkoeppel.sundowner.security.tls.TlsException
import io.jsonwebtoken.JwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {


	@ExceptionHandler(InvalidSpotInputException::class)
	fun handleBadRequestException(
		exception: Exception, request: WebRequest
	): ResponseEntity<ErrorDetails> {
		val errorDetails = ErrorDetails(
			timestamp = LocalDateTime.now(), message = exception.message, details = request.getDescription(false)
		)
		return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
	}

	@ExceptionHandler(BadCredentialsException::class, AuthenticationServiceException::class, JwtException::class)
	fun handleUnauthorizedException(
		exception: Exception, request: WebRequest
	): ResponseEntity<ErrorDetails> {
		val errorDetails = ErrorDetails(
			timestamp = LocalDateTime.now(), message = exception.message, details = request.getDescription(false)
		)
		return ResponseEntity(errorDetails, HttpStatus.UNAUTHORIZED)
	}

	fun handleNotFoundException(exception: Exception, requestException: WebRequest): ResponseEntity<ErrorDetails> {
		val errorDetails = ErrorDetails(
			timestamp = LocalDateTime.now(),
			message = exception.message,
			details = requestException.getDescription(false)
		)
		return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
	}

	@ExceptionHandler(
		Exception::class, StorageException::class, InvalidCertificateException::class, TlsException::class
	)
	fun handleInternalServerErrorException(
		exception: Exception, request: WebRequest
	): ResponseEntity<ErrorDetails> {
		val errorDetails = ErrorDetails(
			timestamp = LocalDateTime.now(), message = "Internal Server Error", details = request.getDescription(false)
		)
		return ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
	}

	fun handleBadGatewayException(
		exception: Exception, request: WebRequest
	): ResponseEntity<ErrorDetails> {
		val errorDetails = ErrorDetails(
			timestamp = LocalDateTime.now(),
			message = "Connection to 3rd party service failed.",
			details = request.getDescription(false)
		)
		return ResponseEntity(errorDetails, HttpStatus.BAD_GATEWAY)
	}
}