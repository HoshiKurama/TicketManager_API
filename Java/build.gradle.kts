plugins {
    kotlin("jvm") version "2.2.0"
    id("com.github.ben-manes.versions") version "0.52.0"
}

dependencies {
    implementation(project(":Common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
}

kotlin {
    jvmToolchain(21)
}