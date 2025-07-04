package de.tkoeppel.sundowner.module.spots

import de.tkoeppel.sundowner.basetype.SpotStatus
import de.tkoeppel.sundowner.dao.SpotDAO
import de.tkoeppel.sundowner.exceptions.LimitExceededException
import de.tkoeppel.sundowner.mapper.SpotMapper
import de.tkoeppel.sundowner.po.SpotPO
import de.tkoeppel.sundowner.to.spots.CreateSpotTO
import de.tkoeppel.sundowner.to.spots.MapSpotTO
import org.locationtech.jts.geom.Coordinate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

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

	fun createSpot(createSpotTO: CreateSpotTO): Long {
		// TODO check for nearby point min. 10m or sth. allowed
		// TODO check for inappropiate description

		val name = "" // TODO Reverse Geocode?
		val coordinate = Coordinate(createSpotTO.location.lng, createSpotTO.location.lat)
		val po = SpotPO(
			createSpotTO.type,
			coordinate,
			name,
			createSpotTO.description,
			0.0,
			"" /* TODO */,
			ZonedDateTime.now(),
			createSpotTO.transport,
			SpotStatus.DRAFT)
		val saved = this.spotDAO.save(po)
		return saved.id
	}

}