plugins {
    alias(libs.plugins.kotlin.noarg)
}

noArg {
    annotation("jakarta.persistence.Entity")
}

dependencies {
    implementation(project(":common"))

    implementation(libs.kotlin.reflect)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.jakarta.persistence.api)
    implementation(libs.jts.core)
    implementation(libs.hibernate.spatial)
    implementation(libs.postgresql)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.springdoc.openapi.starter.webmvc.ui)
    testImplementation(libs.bundles.test)
}

tasks.bootJar {
    enabled = false
}
