package com.github.hoshikurama.ticketmanager.api.registry.database.utils

import com.github.hoshikurama.ticketmanager.api.ticket.Assignment
import com.github.hoshikurama.ticketmanager.api.ticket.Creator
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket

/**
 * Search Constraints are how TicketManager forwards search criteria to database extensions. Each property is encapsulated
 * with an "Option" wrapper, which makes two important distinctions:
 * - Null values
 * - Options containing null
 *
 * A null option is not part of a search. However, an Option containing null means the search should be performed on a null value.
 * For example, assigned = null means assignment is not a criteria. However, assigned = Option(null) means to search
 * for tickets assigned to nobody.
 *
 * @property creator search by ticket creator
 * @property assigned search by ticket assignment
 * @property priority search by ticket priority
 * @property status search by ticket status
 * @property closedBy search for tickets which have been closed by a certain entity at any point
 * @property lastClosedBy search for tickets in which that last close was by a certain entity
 * @property world search by tickets created in a certain world
 * @property creationTime search by tickets newer than a specific epoch time
 * @property keywords search by tickets with a particular keyword in any of its comments or in its opening statement.
 * @property requestedPage Page requested by user. Default to closest value if request falls outside of possible range.
 */
@Suppress("MemberVisibilityCanBePrivate")
class SearchConstraints(
    val creator: Option<Creator>? = null,
    val assigned: Option<Assignment>? = null,
    val priority: Option<Ticket.Priority>? = null,
    val status: Option<Ticket.Status>? = null,
    val closedBy: Option<Creator>? = null,
    val lastClosedBy: Option<Creator>? = null,
    val world: Option<String>? = null,
    val creationTime: Option<Long>? = null,
    val keywords: Option<List<String>>? = null,
    val requestedPage: Int,
) {
    /**
     * Ticket searches can use up to 4 different symbols in searches. In practice, this is usually
     * limited to only two. As the name implies, these are the symbols a user may enter and represent
     * all possible relational types.
     */
    @Suppress("Unused")
    enum class Symbol {
        EQUALS, NOT_EQUALS, LESS_THAN, GREATER_THAN
    }
}

/**
 * Option is used exclusively by the SearchConstraints type. It allows for a differentiation between null values
 * (no search) and values which search for null. It also carries the appropriate symbol.
 *
 * See SearchConstraints for more information
 *
 * (Note: Java users may wonder why Java's own Optional<T> wasn't used. Simply put, Kotlin provides a much nicer system
 * and syntax for handling nullability, and so this custom object works much better.)
 * @see SearchConstraints
 */
@Suppress("Unused")
class Option<T>(val symbol: SearchConstraints.Symbol, val value: T)