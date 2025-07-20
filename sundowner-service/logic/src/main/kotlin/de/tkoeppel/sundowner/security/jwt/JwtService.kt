package de.tkoeppel.sundowner.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


@Service
class JwtService(private val jwtConfig: JwtConfig) {
	private val signingKey: SecretKey by lazy {
		val keyBytes = jwtConfig.secretKey.toByteArray()
		require(keyBytes.size >= 32) {
			"Secret key for HmacSHA256 must be at least 256 bits (32 bytes) long."
		}
		SecretKeySpec(keyBytes, "HmacSHA256")
	}

	fun generateToken(subject: String, expiresAt: Date, additionalClaims: Map<String, Any> = emptyMap()): String {
		return Jwts.builder() //
			.claims(additionalClaims) //
			.subject(subject) //
			.issuedAt(Date(System.currentTimeMillis())) //
			.expiration(expiresAt) //
			.signWith(signingKey) //
			.compact()
	}

	fun extractUsername(token: String): String {
		return extractAllClaims(token).subject
	}

	private fun extractAllClaims(token: String): Claims {
		return Jwts.parser() //
			.verifyWith(signingKey) //
			.build() //
			.parseSignedClaims(token) //
			.payload
	}
}