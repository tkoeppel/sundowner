package de.tkoeppel.sundowner.dao

import de.tkoeppel.sundowner.po.SpotReviewPO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SpotReviewDAO : JpaRepository<SpotReviewPO, Long> {

	@Query(
		"""
        SELECT
            s.id as spotId,
            AVG(r.rating) as averageRating
        FROM
            SpotPO s LEFT JOIN SpotReviewPO r ON s.id = r.spot.id
        WHERE
            s.id IN :spotIds
    """
	)
	fun getAverageRatingBySpotIds(spotIds: List<Long>): List<SpotAverageRating>

}