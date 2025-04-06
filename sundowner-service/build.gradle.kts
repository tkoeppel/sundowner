plugins {
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.spring.dependency.management) apply false
    alias(libs.plugins.kotlin.noarg) apply false
    jacoco
    java
}

group = "de.tkoeppel.sundowner"
version = "0.0.1-SNAPSHOT"

val javaVersion = JavaVersion.VERSION_21

allprojects {
    plugins.apply("jacoco")
    plugins.apply("java")

    repositories {
        mavenCentral()
    }

    tasks.test {
        useJUnitPlatform()
        finalizedBy(tasks.jacocoTestReport)
    }

    tasks.jacocoTestReport {
        dependsOn(tasks.test)
        reports {
            xml.required.set(true)
        }
    }
}

subprojects {
    plugins.apply(rootProject.libs.plugins.kotlin.jvm.get().pluginId)
    plugins.apply(rootProject.libs.plugins.kotlin.spring.get().pluginId)
    plugins.apply(rootProject.libs.plugins.spring.dependency.management.get().pluginId)
    plugins.apply(rootProject.libs.plugins.spring.boot.get().pluginId)

    tasks.withType<JavaCompile> {
        sourceCompatibility = javaVersion.toString()
        targetCompatibility = javaVersion.toString()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget(javaVersion.toString()))
        }
    }

    tasks.test {
        maxHeapSize = "1G"
    }
}

