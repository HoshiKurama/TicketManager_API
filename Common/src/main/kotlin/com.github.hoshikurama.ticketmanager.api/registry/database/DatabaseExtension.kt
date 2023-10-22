package com.github.hoshikurama.ticketmanager.api.registry.database

import com.github.hoshikurama.ticketmanager.api.registry.config.Config
import com.github.hoshikurama.ticketmanager.api.registry.locale.Locale
import java.nio.file.Path

fun interface DatabaseExtension {
    suspend fun load(tmDirectory: Path, config: Config, locale: Locale): AsyncDatabase
}