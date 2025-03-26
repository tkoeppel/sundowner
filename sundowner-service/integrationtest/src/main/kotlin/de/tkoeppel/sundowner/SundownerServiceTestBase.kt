package de.tkoeppel.sundowner

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import de.tkoeppel.sundowner.dao.PhotoDAO
import de.tkoeppel.sundowner.dao.SpotDAO
import de.tkoeppel.sundowner.dao.SpotReviewDAO
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
open class SundownerServiceTestBase {

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

	protected val API_VERSION = "v1"
	protected val API_PATH = "/api/" + API_VERSION


	protected fun <T> convertToTO(result: MvcResult, typeRef: TypeReference<T>): T {
		val objStr = result.response.contentAsString
		return this.mapper.readValue(objStr, typeRef)
	}

	protected fun doTestBadRequest(reqBuilder: RequestBuilder, message: String) {
		val result = this.mockMvc.perform(reqBuilder).andExpect(status().isBadRequest).andReturn()
		val exception = this.convertToTO(result, object : TypeReference<Exception>() {})
		assertThat(exception.message).contains(message)
	}

}