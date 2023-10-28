package com.github.hoshikurama.ticketmanager.api.registry.config

import java.nio.file.Path

/**
 * Represents a builder which provides a Config instance to TicketManager. #load() does not assume that files exists.
 *
 * While this extension is registered only once, the #load() function will be called on server startup and any time
 * the plugin is reloaded.
 */
fun interface ConfigExtension {
    /**
     * constructs a Config instance.
     * @param tmDirectory TicketManager directory
     */
    suspend fun load(tmDirectory: Path): Config
}