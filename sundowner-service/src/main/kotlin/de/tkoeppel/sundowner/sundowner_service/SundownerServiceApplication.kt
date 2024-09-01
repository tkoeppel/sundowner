package de.tkoeppel.sundowner.sundowner_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SundownerServiceApplication

fun main(args: Array<String>) {
	runApplication<SundownerServiceApplication>(*args)
}
