plugins {
    kotlin("jvm")
    id("com.github.ben-manes.versions") version "0.51.0"
    java
}

dependencies {
    implementation(project(":Common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
}

kotlin {
    jvmToolchain(17)
}