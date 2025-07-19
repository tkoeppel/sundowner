package de.tkoeppel.sundowner.po

import jakarta.persistence.*
import java.time.ZonedDateTime


@Entity
@Table(name = "refresh_tokens")
class RefreshTokenPO(
	@Column(name = "token", nullable = false, unique = true) val token: String,

	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") val user: UserPO,

	@Column(name = "expires_at", nullable = false) val expires_at: ZonedDateTime,

	@Column(name = "created_at", nullable = false) val created_at: ZonedDateTime = ZonedDateTime.now()
) : BasePO()