package com.github.hoshikurama.ticketmanager.api.registry.config

import kotlin.time.Duration

@Suppress("Unused")
/**
 * This interface specifies what any config MUST provide to TicketManager. Nullable variables
 * indicate that the particular field was set to enabled if it exists and false if disabled.
 */
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