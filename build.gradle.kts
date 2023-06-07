plugins {
    kotlin("jvm") version "1.8.21"
    `maven-publish`
    java
}

repositories {
    mavenCentral()
}

dependencies {

}

allprojects {
    group = "com.github.hoshikurama"
    version = "10.0.0-RC"
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
    }

    tasks {
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