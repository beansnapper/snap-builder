plugins {
    kotlin("jvm") version "1.4.20"
    kotlin("kapt") version "1.4.20"
}

group = "sample"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    compileOnly(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.20")
    compileOnly(project(":annotation"))
    kapt(project(":processor"))


    testImplementation("io.kotest:kotest-runner-junit5:4.3.1")
    testImplementation("io.kotest:kotest-assertions-core:4.3.1")
    testImplementation("io.kotest:kotest-property:4.3.1")
}
