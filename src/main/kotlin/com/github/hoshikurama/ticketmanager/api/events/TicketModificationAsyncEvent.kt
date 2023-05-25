package com.github.hoshikurama.ticketmanager.api.events

import com.github.hoshikurama.ticketmanager.api.commands.CommandSender
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket

/**
 * This event represents any successful ticket modification request. Most developers are interested in this
 * event as it contains the most data. The event will fire once a command successfully begins execution.
 *
 * To increase the user-perceived speed of the plugin, messages are usually written before the database
 * write completes. Thus, only use this event if you do not care whether the database has finished
 * writing. Users who must ensure the command completes execution should see DatabaseWriteCompleteEventAsync.
 *
 * Event fires once the command successfully begins execution.
 */
interface TicketModificationAsyncEvent {
    val commandSender: CommandSender.Active
    val ticketCreator: Ticket.Creator
    val modification: Ticket.Action<*,*,*>
    val wasSilent: Boolean
    fun callEventTM()
}

// TODO: DO I WANT TicketModificationAsyncEvent<*>