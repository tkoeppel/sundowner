plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":data"))

    implementation(libs.spring.boot.starter.test)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.jts.core)
    implementation(libs.hibernate.spatial)
    implementation(libs.jakarta.servlet.api)
    implementation(libs.jackson.module.kotlin)

    testImplementation(libs.spring.boot.starter.web)
    testImplementation(libs.spring.boot.starter.test)

    testRuntimeOnly(project(":logic"))
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

tasks.bootJar {
    enabled = false
}
