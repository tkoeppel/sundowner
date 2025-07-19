package de.tkoeppel.sundowner.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.boot.context.properties.bind.DefaultValue

@ConfigurationProperties(prefix = "security.jwt")
data class JwtConfig @ConstructorBinding constructor(
	@DefaultValue("") val secretKey: String,
	@DefaultValue(3600000.toString()) val accessTokenExpiration: Long,
	@DefaultValue(8640000.toString()) val refreshTokenExpiration: Long

)