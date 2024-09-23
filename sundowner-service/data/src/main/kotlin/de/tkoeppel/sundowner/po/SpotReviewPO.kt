package de.tkoeppel.sundowner.po

import jakarta.persistence.*

@Entity
@Table(name = "spot_reviews")
data class SpotReviewPO(

	@ManyToOne
	@JoinColumn(name = "spot", nullable = false)
	val spot: Spot,

	@ManyToOne
	@JoinColumn(name = "review_user", nullable = false)
	val reviewUser: UserPO,

	@Column(name = "rating", nullable = false)
	val rating: Int,

	@Column(name = "comment")
	val comment: String?
) : BasePO()