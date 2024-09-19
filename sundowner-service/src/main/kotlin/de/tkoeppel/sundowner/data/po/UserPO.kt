package de.tkoeppel.sundowner.data.po

import jakarta.persistence.Column
import jakarta.persistence.Table
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import java.time.ZonedDateTime

@Entity
@Table(name = "users")
data class UserPO(
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	val id: Long,

	@Column(name = "register_date", nullable = false)
	val registerDate: ZonedDateTime,

	@Column(name = "last_active_date", nullable = false)
	val lastActiveDate: ZonedDateTime,

	@Column(name = "last_used_username", nullable = false)
	val lastUsedUsername: String
)
