val mockitoAgent = configurations.create("mockitoAgent")

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.minio)
    implementation(libs.httpclient5)
    implementation(libs.ktor.network.tls.certificates)

    // spatial data
    implementation(libs.jts.core)
    implementation(libs.hibernate.spatial)
    implementation(libs.jackson.datatype.jts)

    runtimeOnly(libs.postgresql)

    testImplementation(libs.bundles.test)
    mockitoAgent(libs.mockito.core) { isTransitive = false }

    // sundowner dependencies
    implementation(project(":data"))
    implementation(project(":common"))

    // Open API
    implementation(libs.springdoc.openapi.starter.webmvc.api)
}

sourceSets {
    test {
        resources {
            srcDir("src/test/resources")
        }
    }
}

tasks.test {
    jvmArgs("-javaagent:${mockitoAgent.asPath}")
}

tasks.processTestResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

