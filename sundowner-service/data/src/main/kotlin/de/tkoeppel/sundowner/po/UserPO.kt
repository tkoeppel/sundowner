package de.tkoeppel.sundowner.po

import jakarta.persistence.*
import java.time.ZonedDateTime


@Entity
@Table(name = "users")
data class UserPO(
	@Column(name = "username", nullable = false, unique = true) val username: String,

	@Column(name = "password_hash", nullable = false, length = 72) val passwordHash: String,

	@Column(name = "email", unique = true, nullable = false) var email: String,

	@Column(name = "active", nullable = false) val active: Boolean = false,

	@Column(name = "created_at", nullable = false) val createdAt: ZonedDateTime,

	@ElementCollection(fetch = FetchType.EAGER) @CollectionTable(
		name = "authorities", joinColumns = [JoinColumn(name = "user_id")]
	) @Column(name = "authority") val authorities: Set<String>

) : BasePO()