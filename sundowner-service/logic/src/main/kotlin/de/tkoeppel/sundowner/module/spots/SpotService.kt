package de.tkoeppel.sundowner.module.spots

import de.tkoeppel.sundowner.dao.SpotDAO
import de.tkoeppel.sundowner.to.MapViewTO
import de.tkoeppel.sundowner.to.SpotTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SpotService {

	@Autowired
	private lateinit var spotDAO: SpotDAO

	fun getPointsInView(max: Int, mapViewTO: MapViewTO): List<SpotTO> {
		// TODO
		return listOf()
	}
}