package de.tkoeppel.sundowner.po

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "users")
data class UserPO (

	@Column(name = "register_date", nullable = false) val registerDate: ZonedDateTime,

	@Column(name = "last_active_date", nullable = false) val lastActiveDate: ZonedDateTime,

	@Column(name = "last_used_username", nullable = false, unique = true) val lastUsedUsername: String
) : BasePO()
