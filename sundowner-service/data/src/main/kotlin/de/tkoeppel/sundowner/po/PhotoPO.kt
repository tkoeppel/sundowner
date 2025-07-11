package de.tkoeppel.sundowner.po

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "photos")
data class PhotoPO(

	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "spot_id") val spot: SpotPO? = null,

	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "review_id") val review: SpotReviewPO? = null,

	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) val uploadedBy: UserPO,

	@Column(name = "uploaded_date", nullable = false) val uploadedDate: ZonedDateTime,

	@Column(name = "url", nullable = false) val url: String
) : BasePO()
