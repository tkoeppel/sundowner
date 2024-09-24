package de.tkoeppel.sundowner

import org.springframework.boot.SpringBootConfiguration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
@EnableJpaRepositories(basePackages = ["de.tkoeppel.sundowner.dao"])
@SpringBootConfiguration
class SundownerServiceTestConfiguration {}