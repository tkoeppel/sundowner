package de.tkoeppel.sundowner.util;

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.safari.SafariOptions
import org.openqa.selenium.safari.SafariDriver
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.remote.RemoteWebDriver
import io.github.bonigarcia.wdm.config.DriverManagerType

class DriverManager {
  private val defaultBrowser: String =  DriverManagerType.CHROME.toString();
  private val browser = System.getProperty("browser", defaultBrowser).lowercase()

  fun setup(): RemoteWebDriver {
    return when (browser) {
      "chrome" -> {
        val options = ChromeOptions()
        ChromeDriver(options)
      }
      "firefox" -> {
        val options = FirefoxOptions()
        FirefoxDriver(options)
      }
      "safari" -> {
        val options = SafariOptions()
        SafariDriver(options)
      }
      "edge" -> {
        val options = EdgeOptions()
        EdgeDriver(options)
      }
      else -> throw IllegalArgumentException("Unsupported browser: $browser")
    }
  }
}
