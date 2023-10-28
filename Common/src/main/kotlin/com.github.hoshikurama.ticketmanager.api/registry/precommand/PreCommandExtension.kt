package com.github.hoshikurama.ticketmanager.api.registry.precommand

import com.github.hoshikurama.ticketmanager.api.CommandSender
import com.github.hoshikurama.ticketmanager.api.registry.locale.Locale
import com.github.hoshikurama.ticketmanager.api.registry.permission.Permission

@Suppress("Unused")
/**
 * Represents an Extension which is run before a TicketManager command begins execution. The three types are outlined
 * below.
 *
 * Note: all types run on the internal off-thread coroutine dispatcher pool.
 */
sealed interface PreCommandExtension {

    /**
     * SyncDeciders run first. They are executed in order of registration time and have the unique ability to block
     * the command from running. All SyncDeciders must agree to run the command, and the first to block execution will
     * short-circuit.
     */
    fun interface SyncDecider : PreCommandExtension {
        suspend fun beforeCommand(
            sender: CommandSender.Active,
            permission: Permission,
            locale: Locale,
        ): Decision

        /**
         * - CONTINUE: Continue to the next SyncDecider or, if it was the last one, begin command execution.
         * - BLOCK: Short-circuit any remaining checks and block the command.
         */
        enum class Decision {
            CONTINUE, BLOCK
        }
    }

    /**
     * SyncAfters are launched in order of registration, but each entry must complete before the next is started. Note
     * that this type executes alongside command execution.
     */
    fun interface SyncAfter : PreCommandExtension {
        suspend fun afterCommand(
            sender: CommandSender.Active,
            permission: Permission,
            locale: Locale,
        )
    }

    /**
     * AsyncAfters Entries are launched in order of registration to run concurrently. Note that this type executes
     * alongside command execution.
     */
    fun interface AsyncAfter : PreCommandExtension {
        suspend fun afterCommand(
            sender: CommandSender.Active,
            permission: Permission,
            locale: Locale,
        )
    }
}