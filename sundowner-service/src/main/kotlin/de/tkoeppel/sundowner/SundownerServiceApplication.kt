package de.tkoeppel.sundowner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SundownerServiceApplication

fun main(args: Array<String>) {
	runApplication<SundownerServiceApplication>(*args)
}