package com.github.hoshikurama.ticketmanager.api.events

import com.github.hoshikurama.ticketmanager.api.CommandSender
import com.github.hoshikurama.ticketmanager.api.ticket.ActionInfo
import com.github.hoshikurama.ticketmanager.api.ticket.Creator

/**
 * Root event for all ticket modifications or creations
 * @property commandSender User who caused the change. This can be either a player or Console.
 */
sealed interface TicketEvent : TMEvent {
    val commandSender: CommandSender.Active
}

/**
 * Any ticket event where an action pertains to one and only one ticket.
 * @property id id of the affected ticket
 * @property creator creator of the affected ticket
 */
sealed interface SingleTicketEffectEvent : TicketEvent {
    val id: Long
    val creator: Creator
}

/**
 * Any ticket event that can be silent.
 * @property wasSilent was the event executed silently
 */
sealed interface CanBeSilentTicketEvent : TicketEvent {
    val wasSilent: Boolean
}

/**
 * An event in which a ticket becomes closed either directly (close) or indirectly (mass-close)
 */
sealed interface TicketCloseEvent: CanBeSilentTicketEvent


// Implementations

data class TicketCreateEvent(
    override val commandSender: CommandSender.Active,
    override val creator: Creator,
    override val id: Long,
    val modification: ActionInfo.Open,
) : SingleTicketEffectEvent

data class TicketMassCloseEvent(
    override val commandSender: CommandSender.Active,
    override val wasSilent: Boolean,
    val lowerBound: Long,
    val upperBound: Long,
    val modification: ActionInfo.MassClose,
) : CanBeSilentTicketEvent, TicketCloseEvent

data class TicketAssignEvent(
    override val commandSender: CommandSender.Active,
    override val wasSilent: Boolean,
    override val creator: Creator,
    override val id: Long,
    val modification: ActionInfo.Assign,
) : SingleTicketEffectEvent, CanBeSilentTicketEvent

data class TicketCommentEvent(
    override val commandSender: CommandSender.Active,
    override val wasSilent: Boolean,
    override val creator: Creator,
    override val id: Long,
    val modification: ActionInfo.Comment,
) : SingleTicketEffectEvent, CanBeSilentTicketEvent

data class TicketReopenEvent(
    override val commandSender: CommandSender.Active,
    override val creator: Creator,
    override val wasSilent: Boolean,
    override val id: Long,
    val modification: ActionInfo.Reopen,
) : SingleTicketEffectEvent, CanBeSilentTicketEvent

data class TicketCloseWithCommentEvent(
    override val commandSender: CommandSender.Active,
    override val wasSilent: Boolean,
    override val creator: Creator,
    override val id: Long,
    val modification: ActionInfo.CloseWithComment,
) : SingleTicketEffectEvent, TicketCloseEvent


data class TicketCloseWithoutCommentEvent(
    override val commandSender: CommandSender.Active,
    override val wasSilent: Boolean,
    override val creator: Creator,
    override val id: Long,
    val modification: ActionInfo.CloseWithoutComment,
) : SingleTicketEffectEvent, TicketCloseEvent

data class TicketSetPriorityEvent(
    override val commandSender: CommandSender.Active,
    override val creator: Creator,
    override val wasSilent: Boolean,
    override val id: Long,
    val modification: ActionInfo.SetPriority,
) : SingleTicketEffectEvent, CanBeSilentTicketEvent