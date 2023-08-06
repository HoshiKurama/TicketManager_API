plugins {
    kotlin("jvm") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
    java
}

application {
    mainClass.set("com.github.hoshikurama.ticketmanager.api.paper.TicketManagerDatabaseRegister")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20-R0.1-SNAPSHOT")
    implementation(project(":Common"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }

    shadowJar {
        dependencies {
            include(project(":Common"))
        }
    }
}