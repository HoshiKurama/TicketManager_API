plugins {
    kotlin("jvm") version "1.8.21"
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    implementation(project(":Common"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}