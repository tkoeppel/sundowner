package de.tkoeppel.sundowner

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

@EntityScan(basePackages = ["de.tkoeppel.sundowner.po"])
@AutoConfigureTestDatabase
@SpringBootTest
class SundownerServiceTest {

}
