plugins {
  id("org.jetbrains.kotlin.jvm") version "2.0.20"
  id("org.jetbrains.kotlin.plugin.spring") version "2.0.20"
  id("org.springframework.boot") version "3.3.3"
  id("io.spring.dependency-management") version "1.1.6"
}

group = "de.tkoeppel.sundowner"
version = "0.0.1-SNAPSHOT"

kotlin {
    jvmToolchain(21)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

springBoot {
    mainClass.set("de.tkoeppel.sundowner.SundownerServiceApplication")
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
    maven { url = uri("https://plugins.gradle.org/m2/") }
}

/** MAIN */
dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("org.springframework.boot:spring-boot-starter-web")  
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    runtimeOnly("org.postgresql:postgresql") 

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-test-autoconfigure")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

/** TEST */
dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(kotlin("test"))

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

/** INTEGRATIONTEST */
sourceSets {
    create("integrationtest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output  
    }
}

val integrationtestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.testImplementation.get())  
}
val integrationtestRuntimeOnly: Configuration by configurations.getting {
    extendsFrom(configurations.testRuntimeOnly.get())
}

dependencies {
    integrationtestImplementation("org.springframework.boot:spring-boot-starter-test")
    integrationtestImplementation(kotlin("test"))

    integrationtestRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.register<Test>("integrationtest") {
    description = "Runs integration tests."
    group = "verification"

    testClassesDirs = sourceSets["integrationtest"].output.classesDirs  

    classpath = sourceSets["integrationtest"].runtimeClasspath  

    shouldRunAfter(tasks.test)
}