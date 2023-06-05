package com.github.hoshikurama.ticketmanager.api.ticket

import java.time.Instant


/**
 * Contains the contents which every action has. These are stored in the ActionInfo outer class.
 */
sealed interface ActionContents {
    val user: Creator
    val location: ActionLocation
    val timestamp: Long
}

/**
 * Represents any action done upon a ticket, including creation. Actions are immutable and thread-safe. Please note that
 * this interface does not actually contain any extra. It is used for polymorphism purposes.
 */
sealed interface Action : ActionContents

/**
 * Contains the non-action-specific data for an action. The inner classes represent particular action types.
 * @property user user who performed the action on the ticket
 * @property location location where user performed the action on the ticket
 * @property timestamp Epoch time for when the action was performed
 */
class ActionInfo(
     override val user: Creator,
     override val location: ActionLocation,
     override val timestamp: Long = Instant.now().epochSecond,
) : ActionContents {

    /**
     * Ticket assignment action
     */
    inner class Assign(val assignment: Assignment): Action, ActionContents by this@ActionInfo
    /**
     * Ticket comment action
     */
    inner class Comment(val comment: String): Action, ActionContents by this@ActionInfo
    /**
     * Closes a ticket while also leaving a comment.
     */
    inner class CloseWithComment(val comment: String): Action, ActionContents by this@ActionInfo

    /**
     * Opening ticket. Note: the initial message is contained here.
     */
    inner class Open(val message: String): Action, ActionContents by this@ActionInfo
    /**
     * Priority-Change ticket action.
     */
    inner class SetPriority(val priority: Ticket.Priority): Action, ActionContents by this@ActionInfo
    /**
     * Closing ticket action. Note: This is a pure close.
     */
    inner class CloseWithoutComment: Action, ActionContents by this@ActionInfo
    /**
     * Re-open ticket action.
     */
    inner class Reopen: Action, ActionContents by this@ActionInfo
    /**
     * Mass-Close ticket action.
     */
    inner class MassClose: Action, ActionContents by this@ActionInfo
}