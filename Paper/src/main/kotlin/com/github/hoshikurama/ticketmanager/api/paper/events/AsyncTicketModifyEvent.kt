package com.github.hoshikurama.ticketmanager.api.paper.events

import com.github.hoshikurama.ticketmanager.api.common.commands.CommandSender
import com.github.hoshikurama.ticketmanager.api.common.events.AbstractAsyncTicketModifyEvent
import com.github.hoshikurama.ticketmanager.api.common.ticket.Action
import com.github.hoshikurama.ticketmanager.api.common.ticket.Creator
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * Paper-specific implementation of the AsyncTicketModifyEvent.
 * @see AbstractAsyncTicketModifyEvent
 */
@Suppress("Unused")
class AsyncTicketModifyEvent(
    override val commandSender: CommandSender.Active,
    override val ticketCreator: Creator,
    override val modification: Action,
    override val wasSilent: Boolean
) : AbstractAsyncTicketModifyEvent, Event(true) {

    companion object {
        val handlerList = HandlerList()
    }

    override fun getHandlers(): HandlerList = handlerList

    override fun callEventTM() {
        callEvent()
    }
}