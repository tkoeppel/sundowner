package de.tkoeppel.sundowner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.SpringApplication

@SpringBootApplication
class SundownerServiceApplication{
	companion object {
        @JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(SundownerServiceApplication::class.java, *args)
		}
	}
}