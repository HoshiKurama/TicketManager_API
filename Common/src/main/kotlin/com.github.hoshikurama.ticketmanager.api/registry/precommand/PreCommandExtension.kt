package com.github.hoshikurama.ticketmanager.api.registry.precommand

import com.github.hoshikurama.ticketmanager.api.CommandSender
import com.github.hoshikurama.ticketmanager.api.registry.locale.Locale
import com.github.hoshikurama.ticketmanager.api.registry.permission.Permission

@Suppress("Unused")
sealed interface PreCommandExtension {

    fun interface SyncDecider : PreCommandExtension {
        suspend fun beforeCommand(
            sender: CommandSender.Active,
            permission: Permission,
            locale: Locale,
        ): Decision

        enum class Decision {
            CONTINUE, BLOCK
        }
    }

    fun interface SyncAfter : PreCommandExtension {
        suspend fun afterCommand(
            sender: CommandSender.Active,
            permission: Permission,
            locale: Locale,
        )
    }

    fun interface AsyncAfter : PreCommandExtension {
        suspend fun afterCommand(
            sender: CommandSender.Active,
            permission: Permission,
            locale: Locale,
        )
    }
}