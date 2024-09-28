package de.tkoeppel.sundowner

import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@ComponentScan(basePackages = ["de.tkoeppel.sundowner"])
@EntityScan(basePackages = ["de.tkoeppel.sundowner.po"])
@EnableJpaRepositories(basePackages = ["de.tkoeppel.sundowner.dao"])
@SpringBootApplication
class SundownerServiceApplication

fun main(args: Array<String>) {
	runApplication<SundownerServiceApplication>(*args)
}
