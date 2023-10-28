package com.github.hoshikurama.ticketmanager.api.registry.permission

/**
 * Represents a builder which provides a Permission instance to TicketManager.
 *
 * While this extension is registered only once, the #load() function will be called on server startup and any time
 * the plugin is reloaded.
 */
fun interface PermissionExtension {
    /**
     * Constructs the Permission instance.
     */
    suspend fun load(): Permission
}