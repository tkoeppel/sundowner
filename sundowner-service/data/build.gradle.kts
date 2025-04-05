plugins {
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.spring.boot)
}

noArg {
    annotation("jakarta.persistence.Entity")
}

dependencies {
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
