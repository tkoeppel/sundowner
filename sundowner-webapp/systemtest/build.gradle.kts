plugins {
    kotlin("jvm") version "2.0.20"
}

group = "de.tkoeppel.sundowner"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.seleniumhq.selenium:selenium-java:4.25.0")
    implementation("io.github.bonigarcia:webdrivermanager:5.9.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
