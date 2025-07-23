package de.tkoeppel.sundowner.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.spec.SecretKeySpec


@Service
class JwtService(private val jwtConfig: JwtConfig) {

	private lateinit var signingKey: SecretKeySpec

	@PostConstruct
	fun initSigningKey() {
		signingKey = SecretKeySpec(jwtConfig.secretKey.toByteArray(), "HmacSHA256")
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
		val username = extractAllClaims(token).subject
		if (username == null) {
			throw JwtException("Invalid token. Could not extract username.")
		}
		return username
	}


	private fun extractAllClaims(token: String): Claims {
		return Jwts.parser() //
			.verifyWith(signingKey) //
			.build() //
			.parseSignedClaims(token) //
			.payload
	}
}