package de.tkoeppel.sundowner.po

import jakarta.persistence.*
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@Entity
@Table(name = "refresh_tokens")
data class RefreshTokenPO @OptIn(ExperimentalUuidApi::class) constructor(
	@Column(name = "token", nullable = false, unique = true) val token: Uuid,

	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) val user: UserPO,

	@Column(name = "expires_at", nullable = false) val expiresAt: ZonedDateTime,

	@Column(name = "created_at", nullable = false) val createdAt: ZonedDateTime = ZonedDateTime.now()
) : BasePO()