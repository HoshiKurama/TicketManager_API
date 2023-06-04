plugins {
    kotlin("jvm") version "1.8.21"
    `maven-publish`
    application
    java
}

group = "com.github.hoshikurama"
version = "10.0.0-RC"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("net.kyori:adventure-api:4.13.1")
    api("com.google.guava:guava:32.0.0-jre")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    compileJava {
        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()
    }
}

// https://developerlife.com/2021/02/06/publish-kotlin-library-as-gradle-dep/
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.hoshikurama"
            artifactId = "ticketmanager-api"
            version = "10.0.0-RC"

            from(components["java"])
        }
    }
}
