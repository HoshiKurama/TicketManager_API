package com.github.hoshikurama.ticketmanager.api.registry.database

import com.github.hoshikurama.ticketmanager.api.registry.config.Config
import com.github.hoshikurama.ticketmanager.api.registry.locale.Locale
import java.nio.file.Path

/**
 * Represents a builder which provides an AsyncDatabase instance to TicketManager.
 *
 * While this extension is registered only once, the #load() function will be called on server startup and any time
 * the plugin is reloaded.
 */
fun interface DatabaseExtension {
    /**
     * Constructs an AsyncDatabase instance.
     * @param tmDirectory TicketManager directory
     * @param config active Config instance
     * @param locale active Locale instance
     */
    suspend fun load(tmDirectory: Path, config: Config, locale: Locale): AsyncDatabase
}