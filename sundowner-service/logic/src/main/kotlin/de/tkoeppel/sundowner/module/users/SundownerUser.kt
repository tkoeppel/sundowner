package de.tkoeppel.sundowner.module.users

import jakarta.validation.constraints.Email
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.ZonedDateTime

data class SundownerUser(
	val id: Long,
	private val passwordHash: String,
	@Email val email: String,
	val active: Boolean,
	val creationTime: ZonedDateTime,
	val roles: Set<String>
) : UserDetails {
	override fun getAuthorities(): Collection<GrantedAuthority?>? {
		return this.roles.map { SimpleGrantedAuthority(it) }
	}

	override fun getPassword(): String {
		return this.passwordHash
	}

	override fun getUsername(): String {
		return this.username
	}

	override fun isEnabled(): Boolean {
		return this.active
	}

}