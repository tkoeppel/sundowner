package de.tkoeppel.sundowner.data.po

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "photos")
data class PhotoPO (
		@Id
		@Column(name = "id")
		@GeneratedValue(strategy = GenerationType.AUTO)
		val id: Long,

		@ManyToOne
		@JoinColumn(name = "spot_id")
		val spot: Spot? = null,

		@ManyToOne
		@JoinColumn(name = "review_id")
		val review: SpotReviewPO? = null,

		@ManyToOne
		@JoinColumn(name = "uploaded_by", nullable = false)
		val uploadedBy: UserPO,

		@Column(name = "uploaded_date", nullable = false)
		val uploadedDate: ZonedDateTime,

		@Column(name = "url", nullable = false)
		val url: String
)
