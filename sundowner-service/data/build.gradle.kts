plugins {
	id("org.jetbrains.kotlin.jvm") version "2.1.20"
	id("org.jetbrains.kotlin.plugin.spring") version "2.1.20"
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.noarg") version "2.0.20"	
}

noArg {
	annotation("jakarta.persistence.Entity")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("jakarta.persistence:jakarta.persistence-api")
	implementation("org.locationtech.jts:jts-core")
	implementation("org.hibernate.orm:hibernate-spatial")
	implementation("org.postgresql:postgresql")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// Open API
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

	// sundowner dependencies
	testImplementation(kotlin("test"))
}

tasks.test {
	useJUnitPlatform()
}

kotlin {
	jvmToolchain(21)
}

tasks.bootJar {
	enabled = false
}