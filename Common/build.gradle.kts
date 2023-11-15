plugins {
    kotlin("jvm")
    id("com.github.ben-manes.versions") version "0.47.0"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.20")
    implementation("net.kyori:adventure-text-minimessage:4.14.0")
    implementation("net.kyori:adventure-api:4.14.0")
    implementation(project(":TMCoroutine"))
}

kotlin {
    jvmToolchain(17)
}