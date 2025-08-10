plugins {
    kotlin("jvm") version "2.2.0"
    id("com.github.ben-manes.versions") version "0.52.0"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.2.0")
    implementation("net.kyori:adventure-text-minimessage:4.24.0")
    implementation("net.kyori:adventure-api:4.24.0")
    implementation(project(":TMCoroutine"))
}

kotlin {
    jvmToolchain(21)
}