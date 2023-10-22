package com.github.hoshikurama.ticketmanager.api.registry.config

import kotlin.time.Duration

@Suppress("Unused")
interface Config {
    val proxyOptions: Proxy?
    val visualOptions: Visuals
    val cooldownOptions: Cooldown?

    val autoUpdateConfig: Boolean
    val checkForPluginUpdates: Boolean
    val allowUnreadTicketUpdates: Boolean

    interface Cooldown {
        val duration: Duration
    }

    interface Proxy {
        val serverName: String?
        val pbeAllowUpdateCheck: Boolean
    }

    interface Visuals {
        val enableAVC: Boolean
        val requestedLocale: String
        val consistentColourCode: String
    }
}

/* TODO: Move cooldowns to precommand extension (New extension)
// Cooldowns
    val enableCooldowns: Boolean            // replaces allowCooldowns
    val cooldownDuration: Duration          // replaces cooldownSeconds
 */
