plugins {
    kotlin("jvm") version "1.9.0"
    id("com.github.ben-manes.versions") version "0.47.0"
}

dependencies {
    compileOnly("net.kyori:adventure-api:4.14.0")
    compileOnly("net.luckperms:api:5.4")
    compileOnly("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("net.kyori:adventure-text-minimessage:4.14.0")
    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}