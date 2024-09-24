package de.tkoeppel.sundowner.po

import jakarta.persistence.*

@Entity
@Table(name = "spot_reviews")
data class SpotReviewPO(

	@ManyToOne
	@JoinColumn(name = "spot", nullable = false)
	val spot: Spot,

	@Column(name = "review_user", nullable = false)
	val reviewUser: String,

	@Column(name = "rating", nullable = false)
	val rating: Int,

	@Column(name = "comment")
	val comment: String?
) : BasePO()