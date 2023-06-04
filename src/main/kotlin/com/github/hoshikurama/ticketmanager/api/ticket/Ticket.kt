package com.github.hoshikurama.ticketmanager.api.ticket

import com.google.common.collect.ImmutableList

/**
 * Tickets are the foundation of TicketManager; they are both immutable and thread-safe.
 * @property id Unique ID the ticket has been assigned
 * @property creator Ticket creator
 * @property priority Priority level
 * @property status Status (open/closed)
 * @property assignedTo Ticket assignment.
 * @property creatorStatusUpdate Used internally to indicate if the creator has seen the last change to their ticket.
 * @property actions Chronological list of modifications made to the initial ticket.
 */
data class Ticket(
    val id: Long,
    val creator: Creator,
    val priority: Priority,
    val status: Status,
    val assignedTo: Assignment,
    val creatorStatusUpdate: Boolean,
    val actions: ImmutableList<Action> ,
) {

    /**
     * Encapsulates the priority level of a ticket.
     */
    enum class Priority {
        LOWEST, LOW, NORMAL, HIGH, HIGHEST
    }

    /**
     * Encapsulates the status of a ticket, which is either open or closed
     */
    enum class Status {
        OPEN, CLOSED;
    }

    operator fun plus(actions: List<Action>): Ticket {
        return Ticket(id, creator, priority, status, assignedTo, creatorStatusUpdate, ImmutableList.copyOf(actions))
    }

    operator fun plus(action: Action): Ticket {
        val newList = ImmutableList.builder<Action>()
            .addAll(actions)
            .add(action)
            .build()
        return Ticket(id, creator, priority, status, assignedTo, creatorStatusUpdate, newList)
    }
}
/*
//TODO UHH WHAT? THIS IS IMPL SPECIFIC FOR DATABASE

enum class TypeAsEnum {
   ASSIGN, CLOSE, CLOSE_WITH_COMMENT, COMMENT, OPEN, REOPEN, SET_PRIORITY, MASS_CLOSE
}

fun Ticket.Action.Type.getEnum() = when (this) {
   is Ticket.Action.Open -> Ticket.Action.TypeAsEnum.OPEN
   is Ticket.Action.Reopen -> Ticket.Action.TypeAsEnum.REOPEN
   is Ticket.Action.Assign -> Ticket.Action.TypeAsEnum.ASSIGN
   is Ticket.Action.Comment -> Ticket.Action.TypeAsEnum.COMMENT
   is Ticket.Action.MassClose -> Ticket.Action.TypeAsEnum.MASS_CLOSE
   is Ticket.Action.SetPriority -> Ticket.Action.TypeAsEnum.SET_PRIORITY
   is Ticket.Action.CloseWithoutComment -> Ticket.Action.TypeAsEnum.CLOSE
   is Ticket.Action.CloseWithComment -> Ticket.Action.TypeAsEnum.CLOSE_WITH_COMMENT
}
*/