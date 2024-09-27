package de.tkoeppel.sundowner

import de.tkoeppel.sundowner.dao.PhotoDAO
import de.tkoeppel.sundowner.dao.SpotDAO
import de.tkoeppel.sundowner.dao.SpotReviewDAO
import org.locationtech.jts.geom.GeometryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@AutoConfigureMockMvc
@SpringBootTest
class SundownerServiceTestBase {

	@Autowired
	protected lateinit var spotDAO: SpotDAO

	@Autowired
	protected lateinit var spotReviewDAO: SpotReviewDAO

	@Autowired
	protected lateinit var photoDAO: PhotoDAO

	@Autowired
	protected lateinit var geoFactory: GeometryFactory;
}