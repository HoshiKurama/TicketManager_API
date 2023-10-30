package com.github.hoshikurama.ticketmanager.api.impl

import com.github.hoshikurama.ticketmanager.api.impl.registry.*
import com.github.hoshikurama.ticketmanager.api.registry.config.ConfigRegistry
import com.github.hoshikurama.ticketmanager.api.registry.database.DatabaseRegistry
import com.github.hoshikurama.ticketmanager.api.registry.locale.LocaleRegistry
import com.github.hoshikurama.ticketmanager.api.registry.permission.PermissionRegistry
import com.github.hoshikurama.ticketmanager.api.registry.playerjoin.PlayerJoinRegistry
import com.github.hoshikurama.ticketmanager.api.registry.precommand.PreCommandRegistry
import com.github.hoshikurama.ticketmanager.api.registry.repeatingtasks.RepeatingTaskRegistry

object TicketManager {
    val ConfigRegistry: ConfigRegistry = TMConfigRegistry()
    val DatabaseRegistry: DatabaseRegistry = TMDatabaseRegistry()
    val LocaleRegistry: LocaleRegistry = TMLocaleRegistry()
    val PermissionRegistry: PermissionRegistry = TMPermissionRegistry()
    val PlayerJoinRegistry: PlayerJoinRegistry = TMPlayerJoinRegistry()
    val PreCommandRegistry: PreCommandRegistry = TMPreCommandRegistry()
    val RepeatingTaskRegistry: RepeatingTaskRegistry = TMRepeatingTaskRegistry()
    val EventBus = TMEventBus()
}