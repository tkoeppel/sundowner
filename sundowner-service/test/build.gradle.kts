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
    implementation(project(":data"))
    implementation(project(":logic"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.postgresql:postgresql")
    testImplementation("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-test-autoconfigure")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(kotlin("test"))


    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("-XX:+EnableDynamicAgentLoading")
}

kotlin {
    jvmToolchain(21)
}