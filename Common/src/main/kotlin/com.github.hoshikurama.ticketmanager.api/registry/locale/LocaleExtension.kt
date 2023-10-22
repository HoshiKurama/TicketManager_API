package com.github.hoshikurama.ticketmanager.api.registry.locale

import com.github.hoshikurama.ticketmanager.api.registry.config.Config
import java.nio.file.Path

fun interface LocaleExtension {
    suspend fun load(tmDirectory: Path, config: Config): Locale
}