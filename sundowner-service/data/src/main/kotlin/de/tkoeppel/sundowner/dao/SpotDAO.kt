package de.tkoeppel.sundowner.dao

import de.tkoeppel.sundowner.po.SpotPO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SpotDAO : JpaRepository<SpotPO, Long> {
	@Query(
		nativeQuery = true, value = """
        SELECT *
        FROM spots
        WHERE ST_Covers(ST_MakeEnvelope(:minX, :minY, :maxX, :maxY, 4326)::geography, location)
        ORDER BY average_rating DESC NULLS LAST 
        LIMIT :limit;
    """
	)
	fun findPointsInBoundingBox(
		limit: Int, minX: Double, minY: Double, maxX: Double, maxY: Double
	): List<SpotPO>
}