package com.github.hoshikurama.ticketmanager.api.common.events

import com.github.hoshikurama.ticketmanager.api.common.commands.CommandSender
import com.github.hoshikurama.ticketmanager.api.common.ticket.Action
import com.github.hoshikurama.ticketmanager.api.common.ticket.Creator

/**
 * This event represents any ticket modification request. The event will fire once a command
 * successfully begins execution.
 *
 * To increase the user-perceived speed of the plugin, messages are usually written before the database
 * write completes. Thus, only use this event if you do not care whether the database has finished
 * writing.
 *
 */
@Suppress("Unused")
interface AbstractAsyncTicketModifyEvent : TMEvent {
    val commandSender: CommandSender.Active
    val ticketCreator: Creator
    val modification: Action
    val wasSilent: Boolean
}