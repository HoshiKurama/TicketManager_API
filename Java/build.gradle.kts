plugins {
    kotlin("jvm")
    id("com.github.ben-manes.versions") version "0.47.0"
    java
}

dependencies {
    implementation(project(":Common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

kotlin {
    jvmToolchain(17)
}