package com.github.hoshikurama.ticketmanager.api.events

import com.github.hoshikurama.ticketmanager.api.CommandSender
import com.github.hoshikurama.ticketmanager.api.ticket.Action
import com.github.hoshikurama.ticketmanager.api.ticket.ActionInfo
import com.github.hoshikurama.ticketmanager.api.ticket.Creator

/**
 * Represents all events related to tickets
 * @property commandSender User who caused the event to be fired (player or console)
 */
sealed interface TicketEvent : TMEvent {
    val commandSender: CommandSender.Active

    /**
     * Holds information about a ticket action that has occurred. As outlined in its
     * documentation, an action can represent either a modification to an existing ticket, or it can represent
     * the creation of a new ticket.
     * @property action The action which occurred
     */
    sealed interface WithAction : TicketEvent {
        val action: Action
    }

    /**
     * Both indicates that an event can be executed silently and contains information pertaining to if the event was
     * caused by a command executed silently.
     * @property wasSilent was the event executed silently
     */
    sealed interface CanBeSilent : TicketEvent {
        val wasSilent: Boolean
    }

    /**
     * Represents a ticket event containing an action that impacts only one ticket.
     * @property creator creator of the impacted ticket
     * @property action performed on the ticket
     * @property id id of the affected ticket
     */
    sealed interface SingleTicketAction : WithAction  {
        val creator: Creator
        val id: Long
    }

    /**
     * Represents a single ticket being closed with or without a comment. Mass-closes are **not** included because it is an
     * aggregate action that affects multiple tickets.
     */
    sealed interface Close : SingleTicketAction, CanBeSilent
}

// Individual events

data class TicketCreateEvent(
    override val commandSender: CommandSender.Active,
    override val creator: Creator,
    override val id: Long,
    override val action: ActionInfo.Open,
) : TicketEvent.SingleTicketAction

data class TicketMassCloseEvent(
    override val commandSender: CommandSender.Active,
    override val action: ActionInfo.MassClose,
    override val wasSilent: Boolean,
    val lowerBound: Long,
    val upperBound: Long,
) : TicketEvent.CanBeSilent, TicketEvent.WithAction

data class TicketAssignEvent(
    override val commandSender: CommandSender.Active,
    override val wasSilent: Boolean,
    override val creator: Creator,
    override val id: Long,
    override val action: ActionInfo.Assign,
) : TicketEvent.SingleTicketAction, TicketEvent.CanBeSilent

data class TicketCommentEvent(
    override val commandSender: CommandSender.Active,
    override val wasSilent: Boolean,
    override val creator: Creator,
    override val id: Long,
    override val action: ActionInfo.Comment,
) : TicketEvent.SingleTicketAction, TicketEvent.CanBeSilent

data class TicketReopenEvent(
    override val commandSender: CommandSender.Active,
    override val creator: Creator,
    override val wasSilent: Boolean,
    override val id: Long,
    override val action: ActionInfo.Reopen,
) : TicketEvent.SingleTicketAction, TicketEvent.CanBeSilent

data class TicketCloseWithCommentEvent(
    override val commandSender: CommandSender.Active,
    override val wasSilent: Boolean,
    override val creator: Creator,
    override val id: Long,
    override val action: ActionInfo.CloseWithComment,
) : TicketEvent.Close

data class TicketCloseWithoutCommentEvent(
    override val commandSender: CommandSender.Active,
    override val wasSilent: Boolean,
    override val creator: Creator,
    override val id: Long,
    override val action: ActionInfo.CloseWithoutComment,
) : TicketEvent.Close

data class TicketSetPriorityEvent(
    override val commandSender: CommandSender.Active,
    override val wasSilent: Boolean,
    override val creator: Creator,
    override val id: Long,
    override val action: ActionInfo.SetPriority,
) : TicketEvent.SingleTicketAction, TicketEvent.CanBeSilent

/**
 * Similar to SMS read receipts, this represents when a user reads a ticket which has unread changes.
 */
data class TicketReadReceiptEvent(
    override val commandSender: CommandSender.Active,
    val id: Long,
): TicketEvent