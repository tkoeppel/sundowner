package de.tkoeppel.sundowner

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import de.tkoeppel.sundowner.dao.*
import de.tkoeppel.sundowner.module.auth.AuthService
import de.tkoeppel.sundowner.module.geocoding.GeoCodingService
import de.tkoeppel.sundowner.module.users.SundownerUserDetailsService
import de.tkoeppel.sundowner.po.UserPO
import jakarta.annotation.PostConstruct
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ResourceLoader
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.ZonedDateTime


@AutoConfigureMockMvc
@SpringBootTest
open class SundownerServiceTestBase {

	@Autowired
	protected lateinit var spotDAO: SpotDAO

	@Autowired
	protected lateinit var userDAO: UserDAO

	@Autowired
	protected lateinit var spotReviewDAO: SpotReviewDAO

	@Autowired
	protected lateinit var refreshTokenDAO: RefreshTokenDAO

	@Autowired
	protected lateinit var photoDAO: PhotoDAO

	@Autowired
	protected lateinit var mockMvc: MockMvc

	@Autowired
	protected lateinit var mapper: ObjectMapper

	@Autowired
	protected lateinit var passwordEncoder: PasswordEncoder

	@Autowired
	private lateinit var userDetailsService: SundownerUserDetailsService

	@Autowired
	private lateinit var geoCodingService: GeoCodingService

	@Autowired
	private lateinit var authService: AuthService

	@Autowired
	private lateinit var jdbcTemplate: JdbcTemplate

	@Autowired
	private lateinit var resourceLoader: ResourceLoader

	protected val API_VERSION = "v1"
	protected val API_PATH = "/api/" + API_VERSION

	protected lateinit var admin: UserPO
	protected lateinit var user: UserPO


	@Transactional
	@PostConstruct
	fun setupDatabase() {
		this.refreshTokenDAO.deleteAll()
		this.spotReviewDAO.deleteAll()
		this.photoDAO.deleteAll()
		this.spotDAO.deleteAll()
		this.userDAO.deleteAll()

		val admin = UserPO(
			"admin",
			this.passwordEncoder.encode("admin_password"),
			"admin@sundowner.de",
			true,
			ZonedDateTime.now(),
			setOf("ROLE_ADMIN", "ROLE_USER")
		)
		this.admin = this.userDAO.save(admin)

		val user = UserPO(
			"user",
			this.passwordEncoder.encode("user_password"),
			"user@sundowner.de",
			true,
			ZonedDateTime.now(),
			setOf("ROLE_USER")
		)
		this.user = this.userDAO.save(user)

	}

	protected fun <T> convertToTO(result: MvcResult, typeRef: TypeReference<T>): T {
		val objStr = result.response.contentAsString
		return this.mapper.readValue(objStr, typeRef)
	}

	protected fun <T> convertToTO(result: MvcResult, clazz: Class<T>): T {
		val objStr = result.response.contentAsString
		return this.mapper.readValue(objStr, clazz)
	}


	protected fun doTestBadRequest(reqBuilder: RequestBuilder, message: String) {
		val result = this.mockMvc.perform(reqBuilder).andExpect(status().isBadRequest).andReturn()
		val exception = this.convertToTO(result, object : TypeReference<Exception>() {})
		assertThat(exception.message).contains(message)
	}

	protected fun doTestForbidden(reqBuilder: RequestBuilder) {
		this.mockMvc.perform(reqBuilder).andExpect(status().isForbidden)
	}

	protected fun doTestUnauthorized(reqBuilder: RequestBuilder) {
		this.mockMvc.perform(reqBuilder).andExpect(status().isUnauthorized)
	}

}