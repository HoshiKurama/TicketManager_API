plugins {
    kotlin("jvm") version "1.8.22"
}

dependencies {
    compileOnly("net.kyori:adventure-api:4.13.1")
    compileOnly("net.luckperms:api:5.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.7.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    implementation("net.kyori:adventure-api:4.13.1")
    implementation("net.kyori:adventure-text-minimessage:4.13.1")
    api("com.google.guava:guava:32.0.0-jre")
    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}