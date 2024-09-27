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

    implementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.locationtech.jts:jts-core")
    implementation("org.hibernate.orm:hibernate-spatial")


    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testRuntimeOnly(project(":logic"))
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    group = "verification"
    description = "Runs the integration tests."
    maxHeapSize = "1G"

    useJUnitPlatform()

    testLogging {
        events("passed")
    }

    jvmArgs("-XX:+EnableDynamicAgentLoading")

    // Set the classes directory to include both main and logic module's output
    testClassesDirs = files(sourceSets["test"].output.classesDirs, sourceSets["main"].output.classesDirs)

    // Ensure the classpath includes the logic module's dependencies
    classpath = sourceSets["test"].runtimeClasspath + project(":logic").sourceSets["main"].runtimeClasspath
}

kotlin {
    jvmToolchain(21)
}