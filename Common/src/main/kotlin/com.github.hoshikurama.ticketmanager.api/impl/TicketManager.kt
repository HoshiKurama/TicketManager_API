package com.github.hoshikurama.ticketmanager.api.impl

import com.github.hoshikurama.ticketmanager.api.impl.registry.*

object TicketManager {
    val ConfigRegistry = TMConfigRegistry()
    val DatabaseRegistry = TMDatabaseRegistry()
    val LocaleRegistry = TMLocaleRegistry()
    val PermissionRegistry = TMPermissionRegistry()
    val PlayerJoinRegistry = TMPlayerJoinRegistry()
    val PreCommandRegistry = TMPreCommandRegistry()
    val RepeatingTaskRegistry = TMRepeatingTaskRegistry()
    val EventBus = TMEventBus()
}