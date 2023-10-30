package com.github.hoshikurama.ticketmanager.api.events

import com.github.hoshikurama.ticketmanager.api.CommandSender
import com.github.hoshikurama.ticketmanager.api.ticket.ActionInfo
import com.github.hoshikurama.ticketmanager.api.ticket.Creator

/**
 * Root event for all ticket modifications or creations
 */
sealed interface TicketEvent : TMEvent {
    val commandSender: CommandSender.Active
}

/**
 * Events in which an existing ticket is modified in some way.
 */
sealed interface TicketModifyEvent : TicketEvent {
    val wasSilent: Boolean
}

/**
 * An event in which a ticket is closed
 */
sealed interface TicketCloseEvent: TicketModifyEvent

data class TicketCreateEvent(
    override val commandSender: CommandSender.Active,
    val ticketCreator: Creator,
    val wasSilent: Boolean,
    val ticketID: Long,
    val modification: ActionInfo.Open,
) : TicketEvent

data class TicketMassCloseEvent(
    override val commandSender: CommandSender.Active,
    val wasSilent: Boolean,
    val lowerBound: Long,
    val upperBound: Long,
    val modification: ActionInfo.MassClose,
) : TicketEvent

data class TicketAssignEvent(
    override val commandSender: CommandSender.Active,
    val ticketCreator: Creator,
    override val wasSilent: Boolean,
    val ticketID: Long,
    val modification: ActionInfo.Assign,
) : TicketModifyEvent

data class TicketCommentEvent(
    override val commandSender: CommandSender.Active,
    val ticketCreator: Creator,
    override val wasSilent: Boolean,
    val ticketID: Long,
    val modification: ActionInfo.Comment,
) : TicketModifyEvent

data class TicketReopenEvent(
    override val commandSender: CommandSender.Active,
    val ticketCreator: Creator,
    override val wasSilent: Boolean,
    val ticketID: Long,
    val modification: ActionInfo.Reopen,
) : TicketModifyEvent

data class TicketCloseWithCommentEvent(
    override val commandSender: CommandSender.Active,
    val ticketCreator: Creator,
    override val wasSilent: Boolean,
    val ticketID: Long,
    val modification: ActionInfo.CloseWithComment,
) : TicketCloseEvent


data class TicketCloseWithoutCommentEvent(
    override val commandSender: CommandSender.Active,
    val ticketCreator: Creator,
    override val wasSilent: Boolean,
    val ticketID: Long,
    val modification: ActionInfo.CloseWithoutComment,
) : TicketCloseEvent

data class TicketSetPriorityEvent(
    override val commandSender: CommandSender.Active,
    val ticketCreator: Creator,
    override val wasSilent: Boolean,
    val ticketID: Long,
    val modification: ActionInfo.SetPriority,
) : TicketModifyEvent