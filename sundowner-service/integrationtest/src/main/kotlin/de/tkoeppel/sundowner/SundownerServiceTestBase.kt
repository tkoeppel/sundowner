package de.tkoeppel.sundowner

import de.tkoeppel.sundowner.dao.SpotDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = ["de.tkoeppel.sundowner"])
@AutoConfigureMockMvc
@SpringBootTest
class SundownerServiceTestBase {

	@Autowired
	protected lateinit var spotDAO: SpotDAO

}