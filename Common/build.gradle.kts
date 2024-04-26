plugins {
    kotlin("jvm")
    id("com.github.ben-manes.versions") version "0.51.0"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.23")
    implementation("net.kyori:adventure-text-minimessage:4.16.0")
    implementation("net.kyori:adventure-api:4.16.0")
    implementation(project(":TMCoroutine"))
}

kotlin {
    jvmToolchain(17)
}