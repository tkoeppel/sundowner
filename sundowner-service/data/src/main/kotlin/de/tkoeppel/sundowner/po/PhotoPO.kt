package de.tkoeppel.sundowner.po

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "photos", schema = "sundowner_")
data class PhotoPO (

	@ManyToOne
		@JoinColumn(name = "spot_id")
		val spot: Spot? = null,

	@ManyToOne
		@JoinColumn(name = "review_id")
		val review: SpotReviewPO? = null,

	@Column(name = "uploaded_by", nullable = false)
		val uploadedBy: String,

	@Column(name = "uploaded_date", nullable = false)
		val uploadedDate: ZonedDateTime,

	@Column(name = "url", nullable = false)
		val url: String
) : BasePO()
