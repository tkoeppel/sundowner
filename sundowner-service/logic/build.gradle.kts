plugins {
	id("org.jetbrains.kotlin.jvm") version "2.0.20"
	id("org.jetbrains.kotlin.plugin.spring") version "2.0.20"
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
}

repositories {
	mavenCentral()
}



dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	//implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// spatial data
	implementation("org.locationtech.jts:jts-core")
	implementation("org.hibernate.orm:hibernate-spatial")
	implementation("org.n52.jackson:jackson-datatype-jts:1.2.10")


	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.apache.httpcomponents.client5:httpclient5")


	// sundowner dependencies
	implementation(project(":data"))

	// Open API
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.6.0")
}

tasks.test {
	useJUnitPlatform()
}

kotlin {
	jvmToolchain(21)
}