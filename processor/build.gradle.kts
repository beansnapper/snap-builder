plugins {
    kotlin("jvm") version "1.4.20"
    kotlin("kapt") version "1.4.20"
}

group = "io.beansnapper.builder"
version = "1.0-SNAPSHOT"


tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation( "com.squareup:kotlinpoet:1.7.2")
    implementation("io.github.microutils:kotlin-logging:1.7.8")
    implementation(project(":annotation"))

    testImplementation("io.kotest:kotest-runner-junit5:4.3.1")
    testImplementation("io.kotest:kotest-assertions-core:4.3.1")
    testImplementation("io.kotest:kotest-property:4.3.1")
}
