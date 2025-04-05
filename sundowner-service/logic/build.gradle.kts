plugins {
	id("org.jetbrains.kotlin.jvm") version "2.1.20"
	id("org.jetbrains.kotlin.plugin.spring") version "2.1.20"
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.6"
}

repositories {
	mavenCentral()
}


val mockitoAgent = configurations.create("mockitoAgent")

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.minio:minio:8.5.17")
	implementation("org.apache.httpcomponents.client5:httpclient5")
	implementation("io.ktor:ktor-network-tls-certificates:3.0.3")


	// spatial data
	implementation("org.locationtech.jts:jts-core")
	implementation("org.hibernate.orm:hibernate-spatial")
	implementation("org.n52.jackson:jackson-datatype-jts:1.2.10")


	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito:mockito-core:5.15.2")
	mockitoAgent("org.mockito:mockito-core:5.15.2") { isTransitive = false }
	testImplementation("org.mockito:mockito-junit-jupiter:5.14.2")
	testImplementation("org.assertj:assertj-core")


	// sundowner dependencies
	implementation(project(":data"))

	// Open API
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.6.0")
	testImplementation(kotlin("test"))
}

sourceSets {
	test {
		resources {
			srcDir("src/test/resources")
		}
	}
}

tasks.test {
	useJUnitPlatform()
	jvmArgs("-javaagent:${mockitoAgent.asPath}")
}

tasks.processTestResources {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

kotlin {
	jvmToolchain(21)
}