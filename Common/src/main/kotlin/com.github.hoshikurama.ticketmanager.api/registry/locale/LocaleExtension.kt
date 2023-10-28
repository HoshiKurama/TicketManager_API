package com.github.hoshikurama.ticketmanager.api.registry.locale

import com.github.hoshikurama.ticketmanager.api.registry.config.Config
import java.nio.file.Path

/**
 * Represents a builder which provides a Locale instance to TicketManager.
 *
 * While this extension is registered only once, the #load() function will be called on server startup and any time
 * the plugin is reloaded.
 */
fun interface LocaleExtension {
    /**
     * Constructs a Locale instance.
     * @param tmDirectory TicketManager directory
     * @param config Active config
     */
    suspend fun load(tmDirectory: Path, config: Config): Locale
}