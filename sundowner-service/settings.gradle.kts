pluginManagement {
	repositories {
		maven { url = uri("https://repo.spring.io/milestone") }
		maven { url = uri("https://repo.spring.io/snapshot") }
		gradlePluginPortal()
	}

	resolutionStrategy {
		eachPlugin {
			if (requested.id.id == "org.jetbrains.kotlin.jvm") {
				useVersion("2.0.20") // Example: Enforce Kotlin version
			}
		}
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}


// Project structure
rootProject.name = "sundowner-service"
include("logic")
include("integrationtest")
include("data")
