plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependency.management) apply false
    alias(libs.plugins.kotlin.noarg) apply false
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
    plugins.apply(rootProject.libs.plugins.kotlin.jvm.get().pluginId)
    plugins.apply(rootProject.libs.plugins.kotlin.spring.get().pluginId)
    plugins.apply(rootProject.libs.plugins.spring.dependency.management.get().pluginId)

    tasks.withType<Test> {
        maxHeapSize = "1G"
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = javaVersion.toString()
        targetCompatibility = javaVersion.toString()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
        }
    }
}