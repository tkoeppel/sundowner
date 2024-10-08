package de.tkoeppel.sundowner

import de.tkoeppel.sundowner.util.DriverManager
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.WebDriver

abstract class AbstractTest {

  private val driverManager = DriverManager()
  private lateinit var driver: WebDriver

  @BeforeEach
  fun beforeEachTest(){
    driver = this.driverManager.setup()
  }
}
