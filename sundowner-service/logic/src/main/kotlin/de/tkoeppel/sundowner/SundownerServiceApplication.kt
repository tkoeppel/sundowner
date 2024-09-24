package de.tkoeppel.sundowner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.domain.EntityScan

@EntityScan(basePackages = ["de.tkoeppel.sundowner.po"])
@SpringBootApplication
class SundownerServiceApplicationKt{
	companion object {
        @JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(SundownerServiceApplicationKt::class.java, *args)
		}
	}
}