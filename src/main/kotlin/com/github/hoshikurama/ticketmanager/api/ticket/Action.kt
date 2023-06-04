package com.github.hoshikurama.ticketmanager.api.ticket

import java.time.Instant

/**
 * Represents any action done upon a ticket, including creation. Actions are immutable and thread-safe.
 * @property user user who performed the action on the ticket
 * @property location location where user performed the action on the ticket
 * @property timestamp Epoch time for when the action was performed
 */
class Action(
    val user: Assignment,
    val location: ActionLocation,
    val timestamp: Long = Instant.now().epochSecond,
) {

    /**
     * Ticket assignment action
     */
    inner class Assign(val assignment: Assignment)
    /**
     * Ticket comment action
     */
    inner class Comment(comment: String)
    /**
     * Closes a ticket while also leaving a comment.
     */
    inner class CloseWithComment(val comment: String)

    /**
     * Opening ticket. Note: the initial message is contained here.
     */
    inner class Open(val message: String)
    /**
     * Priority-Change ticket action.
     */
    inner class SetPriority(val priority: Ticket.Priority)
    /**
     * Closing ticket action. Note: This is a pure close.
     */
    inner class CloseWithoutComment
    /**
     * Re-open ticket action.
     */
    inner class Reopen
    /**
     * Mass-Close ticket action.
     */
    inner class MassClose
}

