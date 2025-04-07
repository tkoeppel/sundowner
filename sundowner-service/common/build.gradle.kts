dependencies {
    implementation(libs.springdoc.openapi.starter.webmvc.ui)
    implementation(libs.jackson.module.kotlin)

    testImplementation(libs.bundles.test)
}

tasks.bootJar {
    enabled = false
}