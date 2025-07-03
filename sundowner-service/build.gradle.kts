import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.*
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.spring.dependency.management) apply false
    alias(libs.plugins.kotlin.noarg) apply false

    `java-base`
    jacoco
}

group = "de.tkoeppel.sundowner"
version = "0.0.1-SNAPSHOT"

val javaVersion = JavaVersion.VERSION_21

allprojects {
    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "jacoco")
    apply(plugin = rootProject.libs.plugins.kotlin.jvm.get().pluginId)
    apply(plugin = rootProject.libs.plugins.kotlin.spring.get().pluginId)
    apply(plugin = rootProject.libs.plugins.spring.dependency.management.get().pluginId)
    apply(plugin = rootProject.libs.plugins.spring.boot.get().pluginId)

    tasks.withType<JavaCompile> {
        sourceCompatibility = javaVersion.toString()
        targetCompatibility = javaVersion.toString()
    }
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget(javaVersion.toString()))
            freeCompilerArgs.add("-Xannotation-default-target=param-property")
        }
    }

    tasks.withType<Test> {
        maxHeapSize = "1G"
        configure<JacocoTaskExtension> {
            isEnabled = true
        }
        finalizedBy(tasks.named("jacocoTestReport"))
    }

    tasks.withType<JacocoReport> {
        dependsOn(tasks.named<Test>("test"))
        reports {
            xml.required.set(true)
            html.required.set(true)
        }
    }
}

tasks.register<JacocoReport>("jacocoRootReport") {
    group = "Reporting"
    description = "Generates an aggregated JaCoCo test coverage report for all subprojects"

    dependsOn(subprojects.map { it.tasks.named<Test>("test") })

    val sourceSets = subprojects.map { it.the<SourceSetContainer>()["main"] }
    sourceDirectories.setFrom(files(sourceSets.map { it.allSource.srcDirs }))
    classDirectories.setFrom(files(sourceSets.map { it.output }))

    executionData.setFrom(files(subprojects.flatMap { subproject ->
        subproject.tasks.withType<Test>().map { testTask ->
            testTask.the<JacocoTaskExtension>().destinationFile
        }
    }))

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    doLast {
        logger.lifecycle("Aggregated JaCoCo report generated at: ${reports.xml.outputLocation.get().asFile}")
    }
}

tasks.named("check") {
    dependsOn(tasks.named("jacocoRootReport"))
}
