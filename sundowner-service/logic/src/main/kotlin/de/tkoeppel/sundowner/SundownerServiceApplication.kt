package de.tkoeppel.sundowner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EntityScan(basePackages = ["de.tkoeppel.sundowner.po"])
@EnableJpaRepositories
@SpringBootApplication
@EnableAutoConfiguration
class SundownerServiceApplicationKt{
	companion object {
        @JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(SundownerServiceApplicationKt::class.java, *args)
		}
	}
}