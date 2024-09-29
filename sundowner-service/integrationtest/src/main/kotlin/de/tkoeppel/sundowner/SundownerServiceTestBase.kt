package de.tkoeppel.sundowner

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import de.tkoeppel.sundowner.dao.PhotoDAO
import de.tkoeppel.sundowner.dao.SpotDAO
import de.tkoeppel.sundowner.dao.SpotReviewDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult

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
	protected lateinit var mockMvc: MockMvc

	@Autowired
	protected lateinit var mapper: ObjectMapper

	protected final val API_PATH = "/api"

	protected fun <T> convertToTO(result: MvcResult): T {
		val objStr = result.response.contentAsString
		return this.mapper.readValue(objStr, object : TypeReference<T>() {})
	}
}