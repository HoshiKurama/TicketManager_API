package com.github.hoshikurama.ticketmanager.api.registry.config

import java.nio.file.Path

// Note: allows side effects. Do not assume files exists
fun interface ConfigExtension {
    suspend fun load(tmDirectory: Path): Config
}