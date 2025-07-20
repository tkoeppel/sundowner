package de.tkoeppel.sundowner.po

import jakarta.persistence.*
import java.time.ZonedDateTime


@Entity
@Table(name = "refresh_tokens")
data class RefreshTokenPO(
	@Column(name = "token", nullable = false, unique = true) val token: String,

	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) val user: UserPO,

	@Column(name = "expires_at", nullable = false) val expiresAt: ZonedDateTime,

	@Column(name = "created_at", nullable = false) val createdAt: ZonedDateTime = ZonedDateTime.now()
) : BasePO()