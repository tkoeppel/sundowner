package de.tkoeppel.sundowner.po

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "photos")
data class PhotoPO(

	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "spot_id", nullable = false) val spot: SpotPO? = null,

	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "review_id") val review: SpotReviewPO? = null,

	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) val user: UserPO,

	@Column(name = "uploaded_at", nullable = false) val uploadedAt: ZonedDateTime,

	@Column(name = "url", nullable = false) val url: String
) : BasePO()
