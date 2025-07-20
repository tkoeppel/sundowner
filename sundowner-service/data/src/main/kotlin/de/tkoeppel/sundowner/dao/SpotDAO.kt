package de.tkoeppel.sundowner.dao

import de.tkoeppel.sundowner.po.SpotPO
import de.tkoeppel.sundowner.so.MapSpotSO
import org.springframework.data.jpa.repository.Query

/**
 * SpotDAO The data access object for spots. Uses geometry.
 *
 * @constructor Create empty {@link SpotDAO}
 */
interface SpotDAO : GeneralDAO<SpotPO> {
	@Query(
		nativeQuery = true, value = """
        SELECT
            s.id,
            s.name, 
			ST_X(s.location) AS longitude,
            ST_Y(s.location) AS latitude,
            AVG(r.rating) AS avgRating
        FROM
            spots s
        LEFT JOIN
            spot_reviews r ON s.id = r.spot_id
        WHERE
            ST_Covers(ST_MakeEnvelope(:minX, :minY, :maxX, :maxY, 4326), s.location)
            AND s.status = 'CONFIRMED'
            AND s.type = 'SUNSET'
        GROUP BY
            s.id
        ORDER BY
            avgRating DESC NULLS LAST
        LIMIT :limit
    """
	)
	fun findPointsInBoundingBox(
		limit: Int, minX: Double, minY: Double, maxX: Double, maxY: Double
	): List<MapSpotSO>

	@Query(
		nativeQuery = true, value = """
        SELECT *
        FROM spots
        WHERE ST_DWithin(
	        location::geography,
	        ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)::geography,
	        :radiusInMeters
        )
		"""
	)
	fun findPointsNearby(lng: Double, lat: Double, radiusInMeters: Int): List<SpotPO>
}