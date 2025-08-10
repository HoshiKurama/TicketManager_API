plugins {
    kotlin("jvm") version "2.2.0"
    `maven-publish`
    java
}

repositories {
    mavenCentral()
}

dependencies {}

allprojects {
    group = "com.github.hoshikurama"
    version = "11.2.0"
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
    }

    // https://developerlife.com/2021/02/06/publish-kotlin-library-as-gradle-dep/
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                from(components["java"])
            }
        }
    }
}

kotlin {
    jvmToolchain(21)
}