package com.github.hoshikurama.ticketmanager.api.registry.repeatingtasks

import com.github.hoshikurama.ticketmanager.api.PlatformFunctions
import com.github.hoshikurama.ticketmanager.api.registry.config.Config
import com.github.hoshikurama.ticketmanager.api.registry.database.AsyncDatabase
import com.github.hoshikurama.ticketmanager.api.registry.locale.Locale
import com.github.hoshikurama.ticketmanager.api.registry.permission.Permission
import kotlin.time.Duration

interface RepeatingTaskExtension {
    val frequency: Duration

    suspend fun onRepeat(
        config: Config,
        locale: Locale,
        database: AsyncDatabase,
        permission: Permission,
        platformFunctions: PlatformFunctions,
    )
}