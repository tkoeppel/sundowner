package de.tkoeppel.sundowner.module.spots

import de.tkoeppel.sundowner.dao.SpotDAO
import de.tkoeppel.sundowner.mapper.SpotMapper
import de.tkoeppel.sundowner.to.MapSpotTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SpotService {

	@Autowired
	private lateinit var spotDAO: SpotDAO

	fun getPointsInView(limit: Int, minX: Double, minY: Double, maxX: Double, maxY: Double): List<MapSpotTO> {
		val pos = this.spotDAO.findPointsInBoundingBox(limit, minX, minY, maxX, maxY)
		val mapper = SpotMapper()
		val tos: List<MapSpotTO> = listOf<MapSpotTO>()
		for (po in pos) {
			tos.plus(mapper.mapMapSpot(po))
		}

		return tos
	}
}