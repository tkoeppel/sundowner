package de.tkoeppel.sundowner.module.spots

import de.tkoeppel.sundowner.dao.SpotDAO
import de.tkoeppel.sundowner.exceptions.LimitExceededException
import de.tkoeppel.sundowner.mapper.SpotMapper
import de.tkoeppel.sundowner.to.MapSpotTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SpotService {
	companion object {
		private const val LIMIT_CEILING = 50
	}

	@Autowired
	private lateinit var spotDAO: SpotDAO

	fun getPointsInView(limit: Int, minX: Double, minY: Double, maxX: Double, maxY: Double): List<MapSpotTO> {
		if (limit < 0 || limit > LIMIT_CEILING) {
			throw LimitExceededException("Limit must be between 0 and $LIMIT_CEILING")
		}

		val pos = this.spotDAO.findPointsInBoundingBox(limit, minX, minY, maxX, maxY)
		val mapper = SpotMapper()
		val tos: MutableList<MapSpotTO> = mutableListOf<MapSpotTO>()
		for (po in pos) {
			tos.add(mapper.mapMapSpot(po))
		}

		return tos.toList()
	}
}