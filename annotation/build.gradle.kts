plugins {
    kotlin("jvm") version "1.4.20"
    `java-library`
    `maven-publish`
}

group = "beansnapper"
version = "0.0.1-SNAPSHOT"

tasks.withType<AbstractArchiveTask> {
    setProperty("archiveBaseName", "snap-builder")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "snap-builder"
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("snap-builder")
                description.set("This library contains the annotation to generate builders")
                url.set("http://www.example.com/library")
//                properties.set(
//                    mapOf(
//                        "myProp" to "value",
//                        "prop.with.dots" to "anotherValue"
//                    )
//                )
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("kurtwguenther")
                        name.set("Kurt Guenther")
                        email.set("kurtwguenther@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://example.com/my-library.git")
                    developerConnection.set("scm:git:ssh://example.com/my-library.git")
                    url.set("http://example.com/my-library/")
                }
            }
        }
    }
    repositories {
        mavenLocal()
    }
}

repositories {
    mavenCentral()
}

dependencies {
//        compileOnly("org.jetbrains.kotlin:kotlin-stdlib:1.4.20")
}
