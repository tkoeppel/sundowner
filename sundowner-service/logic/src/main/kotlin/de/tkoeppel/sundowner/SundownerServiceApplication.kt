package de.tkoeppel.sundowner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EntityScan(basePackages = ["de.tkoeppel.sundowner.po"])
@EnableJpaRepositories(basePackages = ["de.tkoeppel.sundowner.dao"])
@SpringBootApplication
class SundownerServiceApplication

fun main(args: Array<String>) {
	runApplication<SundownerServiceApplication>(*args)
}
