pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
	}

	includeBuild("gradle")
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

// Project structure
rootProject.name = "sundowner-service"
include("logic")
include("integrationtest")
include("data")
