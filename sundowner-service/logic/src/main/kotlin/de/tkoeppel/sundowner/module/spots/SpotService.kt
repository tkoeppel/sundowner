package de.tkoeppel.sundowner.module.spots

import de.tkoeppel.sundowner.basetype.spots.SpotStatus
import de.tkoeppel.sundowner.dao.SpotDAO
import de.tkoeppel.sundowner.dao.UserDAO
import de.tkoeppel.sundowner.mapper.SpotMapper
import de.tkoeppel.sundowner.module.geocoding.GeoCodingService
import de.tkoeppel.sundowner.module.users.UserBean
import de.tkoeppel.sundowner.po.SpotPO
import de.tkoeppel.sundowner.to.spots.CoordinateTO
import de.tkoeppel.sundowner.to.spots.CreateSpotTO
import de.tkoeppel.sundowner.to.spots.MapSpotTO
import de.tkoeppel.sundowner.util.SpotNameUtil
import org.locationtech.jts.geom.Coordinate
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class SpotService(
	private val spotDAO: SpotDAO,
	private val userDAO: UserDAO,
	private val geoCodingService: GeoCodingService,
	private val userBean: UserBean,
	private val spotMapper: SpotMapper
) {
	companion object {
		private const val LIMIT_CEILING = 50
	}

	fun getPointsInView(limit: Int, minX: Double, minY: Double, maxX: Double, maxY: Double): List<MapSpotTO> {
		if (limit < 0 || limit > LIMIT_CEILING) {
			throw InvalidSpotInputException("Limit must be between 0 and $LIMIT_CEILING")
		}

		val tos = this.spotDAO.findPointsInBoundingBox(limit, minX, minY, maxX, maxY)
			.map { so -> this.spotMapper.mapMapSpot(so) }

		return tos
	}

	fun createSpot(createSpotTO: CreateSpotTO): Long {
		checkValidCoordinates(createSpotTO.location)
		checkSpotsNearby(createSpotTO.location, 10)

		val reverseGeoCodeDetails = this.geoCodingService.reverseGeocode(createSpotTO.location)
		val name = SpotNameUtil.getSpotName(createSpotTO.type, createSpotTO.location, reverseGeoCodeDetails)
		val user = this.userBean.getCurrentUser()
		val userPO = this.userDAO.getReferenceById(user.id)

		val po = SpotPO(
			createSpotTO.type,
			Coordinate(createSpotTO.location.lng, createSpotTO.location.lat),
			name,
			createSpotTO.description,
			userPO,
			ZonedDateTime.now(),
			createSpotTO.transport,
			SpotStatus.DRAFT
		)
		val saved = this.spotDAO.save(po)
		return saved.id!!
	}

	private fun checkSpotsNearby(location: CoordinateTO, radiusInMeters: Int) {
		val spotsNearby = this.spotDAO.findPointsNearby(location.lng, location.lat, radiusInMeters)
		if (!spotsNearby.isEmpty()) {
			throw InvalidSpotInputException("There is already a spot within $radiusInMeters meters of the given location")
		}
	}

	private fun checkValidCoordinates(coordinateTO: CoordinateTO) {
		if (coordinateTO.lat < -90 || coordinateTO.lat > 90 || coordinateTO.lng < -180 || coordinateTO.lng > 180) {
			throw InvalidSpotInputException("Invalid coordinates")
		}
	}
}
