dependencies {
	implementation(project(":common"))
	implementation(project(":data"))
	implementation(project(":logic"))

	implementation(libs.spring.boot.starter.data.jpa)
	implementation(libs.jts.core)
	implementation(libs.hibernate.spatial)
	implementation(libs.jakarta.servlet.api)
	implementation(libs.jackson.module.kotlin)
	implementation(libs.spring.boot.starter.security)
	implementation(libs.spring.boot.starter.ouath2)

	implementation(libs.bundles.test)
	testImplementation(libs.spring.boot.starter.web)

	testRuntimeOnly(project(":logic"))
}

tasks.test {
	group = "verification"
	description = "Runs the integration tests."

	testLogging {
		events("passed")
	}

	jvmArgs("-XX:+EnableDynamicAgentLoading")

	// Set the classes directory to include both main and logic module's output
	testClassesDirs = files(sourceSets["test"].output.classesDirs, sourceSets["main"].output.classesDirs)

	// Ensure the classpath includes the logic module's dependencies
	classpath = sourceSets["test"].runtimeClasspath + project(":logic").sourceSets["main"].runtimeClasspath
}

tasks.bootJar {
	enabled = false
}
