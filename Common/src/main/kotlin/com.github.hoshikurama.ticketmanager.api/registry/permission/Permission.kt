package com.github.hoshikurama.ticketmanager.api.registry.permission

import com.github.hoshikurama.ticketmanager.api.CommandSender

@Suppress("Unused")
/**
 * Describes what any Permission extension must provide to TicketManager.
 */
interface Permission {
    /**
     * Return all permission groups on the server. This may or may not run on the main thread, but it must instantly acquire
     * the result. Consider a cache if you require asynchronous acquisition.
     */
    fun allGroupNames(): List<String>

    /**
     * Return all permission groups on the server for a particular player. This may or may not run on the main thread,
     * but it must instantly acquire the result. Consider a cache if you require asynchronous acquisition.
     */
    fun groupNamesOf(player: CommandSender.OnlinePlayer): List<String>

    /**
     * Returns if a given player has a particular permission. This may or may not run on the main thread,
     * but it must instantly acquire the result. Consider a cache if you require asynchronous acquisition.
     */
    fun has(player: CommandSender.OnlinePlayer, permission: String): Boolean
}