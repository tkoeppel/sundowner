package de.tkoeppel.sundowner.dao

import de.tkoeppel.sundowner.po.SpotPO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * SpotDAO The data access object for spots. Uses geometry.
 *
 * @constructor Create empty {@link SpotDAO}
 */
interface SpotDAO : JpaRepository<SpotPO, Long> {
	@Query(
		nativeQuery = true, value = """
        SELECT *
        FROM spots
        WHERE ST_Covers(ST_MakeEnvelope(:minX, :minY, :maxX, :maxY, 4326), location)
        ORDER BY average_rating DESC NULLS LAST 
        LIMIT :limit;
    """
	)
	fun findPointsInBoundingBox(
		limit: Int, minX: Double, minY: Double, maxX: Double, maxY: Double
	): List<SpotPO>

	@Query(
		nativeQuery = true, value = """
        SELECT *
        FROM spots
        WHERE ST_DWithin(
	        location::geography,
	        ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)::geography,
	        :radius
        )
		"""
	)
	fun findPointsNearby(lng: Double, lat: Double, radiusInMeters: Int): List<SpotPO>
}