plugins {
    kotlin("jvm") version "2.0.21"
    id("com.github.ben-manes.versions") version "0.51.0"
}

dependencies {
    implementation(project(":Common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}

kotlin {
    jvmToolchain(21)
}