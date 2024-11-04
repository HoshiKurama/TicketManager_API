plugins {
    kotlin("jvm") version "2.0.21"
    id("com.github.ben-manes.versions") version "0.51.0"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.0.21")
    implementation("net.kyori:adventure-text-minimessage:4.17.0")
    implementation("net.kyori:adventure-api:4.17.0")
    implementation(project(":TMCoroutine"))
}

kotlin {
    jvmToolchain(21)
}