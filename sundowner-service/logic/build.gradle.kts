plugins {
	id("org.jetbrains.kotlin.jvm") version "2.0.20"
	id("org.jetbrains.kotlin.plugin.spring") version "2.0.20"
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
}

repositories {
	mavenCentral()
}

springBoot {
	mainClass.set("de.tkoeppel.sundowner.SundownerServiceApplication")
}



dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")


	// spatial data
	implementation("org.locationtech.jts:jts-core")
	implementation("org.hibernate.orm:hibernate-spatial")
	implementation("org.n52.jackson:jackson-datatype-jts:1.2.10")


	runtimeOnly("org.postgresql:postgresql")

	// sundowner dependencies
	implementation(project(":data"))

	// Open API
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.5.0")
}

tasks.test {
	useJUnitPlatform()
}

kotlin {
	jvmToolchain(21)
}