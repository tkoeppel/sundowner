package de.tkoeppel.sundowner

import de.tkoeppel.sundowner.dao.UserDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

@EntityScan(basePackages = ["de.tkoeppel.sundowner.po"])
@AutoConfigureTestDatabase
@SpringBootTest
class SundownerServiceTest {
	@Autowired protected lateinit var userDAO: UserDAO
}
