package com.github.hoshikurama.ticketmanager.api.ticket

import java.time.Instant


/**
 * Represents any action done upon a ticket, including creation. Actions are immutable and thread-safe. Please note that
 * this interface does not actually contain any data. It is used to hold a reference to a particular action, where you must
 * then discover the type to get access to its data via the ActionInfo outer class.
 */
sealed interface Action

/**
 * Contains the non-action-specific data for an action. The inner classes represent particular action types.
 * @property user user who performed the action on the ticket
 * @property location location where user performed the action on the ticket
 * @property timestamp Epoch time for when the action was performed
 */
class ActionInfo(
     val user: Creator,
     val location: ActionLocation,
     val timestamp: Long = Instant.now().epochSecond,
) {

    /**
     * Ticket assignment action
     */
    inner class Assign(val assignment: Assignment): Action
    /**
     * Ticket comment action
     */
    inner class Comment(val comment: String): Action
    /**
     * Closes a ticket while also leaving a comment.
     */
    inner class CloseWithComment(val comment: String): Action

    /**
     * Opening ticket. Note: the initial message is contained here.
     */
    inner class Open(val message: String): Action
    /**
     * Priority-Change ticket action.
     */
    inner class SetPriority(val priority: Ticket.Priority): Action
    /**
     * Closing ticket action. Note: This is a pure close.
     */
    inner class CloseWithoutComment: Action
    /**
     * Re-open ticket action.
     */
    inner class Reopen: Action
    /**
     * Mass-Close ticket action.
     */
    inner class MassClose: Action
}

