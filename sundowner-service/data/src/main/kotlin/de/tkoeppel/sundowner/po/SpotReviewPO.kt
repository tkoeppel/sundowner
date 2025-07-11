package de.tkoeppel.sundowner.po

import jakarta.persistence.*

@Entity
@Table(name = "spot_reviews")
data class SpotReviewPO(

	@ManyToOne(cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY) @JoinColumn(
		name = "spot_id", nullable = false
	) val spot: SpotPO,

	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) val ratedBy: UserPO,

	@Column(name = "rating", nullable = false) val rating: Int,

	@Column(name = "comment") val comment: String?
) : BasePO()