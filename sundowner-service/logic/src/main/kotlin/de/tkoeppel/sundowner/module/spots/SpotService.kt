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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class SpotService {
	companion object {
		private const val LIMIT_CEILING = 50
	}

	@Autowired
	private lateinit var spotDAO: SpotDAO

	@Autowired
	private lateinit var userDAO: UserDAO

	@Autowired
	private lateinit var geoCodingService: GeoCodingService

	@Autowired
	private lateinit var userBean: UserBean


	fun getPointsInView(limit: Int, minX: Double, minY: Double, maxX: Double, maxY: Double): List<MapSpotTO> {
		if (limit < 0 || limit > LIMIT_CEILING) {
			throw LimitExceededException("Limit must be between 0 and $LIMIT_CEILING")
		}

		val tos = this.spotDAO.findPointsInBoundingBox(limit, minX, minY, maxX, maxY)
			.map { so -> SpotMapper().mapMapSpot(so) }

		return tos
	}

	fun createSpot(createSpotTO: CreateSpotTO): Long {
		checkSpotsNearby(createSpotTO.location, 10)
		val revGeoCodeAddress = this.geoCodingService.reverseGeocode(createSpotTO.location)?.address
		val name = SpotNameUtil.getSpotName(createSpotTO.type, createSpotTO.location, revGeoCodeAddress)
		val user = this.userDAO.getReferenceById(this.userBean.getCurrentUser()!!.id)

		val po = SpotPO(
			createSpotTO.type,
			Coordinate(createSpotTO.location.lng, createSpotTO.location.lat),
			name,
			createSpotTO.description,
			user,
			ZonedDateTime.now(),
			createSpotTO.transport,
			SpotStatus.DRAFT
		)
		val saved = this.spotDAO.save(po)
		return saved.id
	}

	private fun checkSpotsNearby(location: CoordinateTO, radiusInMeters: Int) {
		val spotsNearby = this.spotDAO.findPointsNearby(location.lng, location.lat, radiusInMeters)
		if (!spotsNearby.isEmpty()) {
			throw IllegalArgumentException()
		}
	}

}