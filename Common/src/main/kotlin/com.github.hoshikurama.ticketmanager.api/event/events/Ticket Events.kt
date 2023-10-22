package com.github.hoshikurama.ticketmanager.api.event.events

import com.github.hoshikurama.ticketmanager.api.CommandSender
import com.github.hoshikurama.ticketmanager.api.ticket.ActionInfo
import com.github.hoshikurama.ticketmanager.api.ticket.Creator

data class TicketCreateEvent(
    val commandSender: CommandSender.Active,
    val ticketCreator: Creator,
    val wasSilent: Boolean,
    val ticketID: Long,
    val modification: ActionInfo.Open,
) : TMEvent


sealed interface TicketModifyEvent : TMEvent
sealed interface TicketCloseEvent: TMEvent

data class TicketAssignEvent(
    val commandSender: CommandSender.Active,
    val ticketCreator: Creator,
    val wasSilent: Boolean,
    val ticketID: Long,
    val modification: ActionInfo.Assign,
) : TMEvent, TicketModifyEvent

data class TicketCommentEvent(
    val commandSender: CommandSender.Active,
    val ticketCreator: Creator,
    val wasSilent: Boolean,
    val ticketID: Long,
    val modification: ActionInfo.Comment,
) : TMEvent, TicketModifyEvent

data class TicketReopenEvent(
    val commandSender: CommandSender.Active,
    val ticketCreator: Creator,
    val wasSilent: Boolean,
    val ticketID: Long,
    val modification: ActionInfo.Reopen,
) : TMEvent, TicketModifyEvent

data class TicketCloseWithCommentEvent(
    val commandSender: CommandSender.Active,
    val ticketCreator: Creator,
    val wasSilent: Boolean,
    val ticketID: Long,
    val modification: ActionInfo.CloseWithComment,
) : TMEvent, TicketModifyEvent, TicketCloseEvent


data class TicketCloseWithoutCommentEvent(
    val commandSender: CommandSender.Active,
    val ticketCreator: Creator,
    val wasSilent: Boolean,
    val ticketID: Long,
    val modification: ActionInfo.CloseWithoutComment,
) : TMEvent, TicketModifyEvent, TicketCloseEvent

data class TicketSetPriorityEvent(
    val commandSender: CommandSender.Active,
    val ticketCreator: Creator,
    val wasSilent: Boolean,
    val ticketID: Long,
    val modification: ActionInfo.SetPriority,
) : TMEvent, TicketModifyEvent

data class TicketMassCloseEvent(
    val commandSender: CommandSender.Active,
    val wasSilent: Boolean,
    val lowerBound: Long,
    val upperBound: Long,
    val modification: ActionInfo.MassClose,
) : TMEvent, TicketModifyEvent



