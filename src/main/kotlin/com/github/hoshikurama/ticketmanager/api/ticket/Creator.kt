package com.github.hoshikurama.ticketmanager.api.ticket

import java.util.*

/**
 * Represents, identifies, and compares entities which can create or modify a ticket. It is strictly used for
 * ticket action purposes.
 */
sealed interface Creator {
    /**
     * Compare two creators
     */
    infix fun equalTo(other: Creator): Boolean

    /**
     * Normal player on a Ticket/Action
     * @property uuid Player's unique ID on the server/network.
     */
    class User(val uuid: UUID) : Creator {
        override infix fun equalTo(other: Creator): Boolean = other is User && this.uuid == other.uuid
    }

    /**
     * Console on a Ticket/Action
     */
    object Console : Creator {
        override infix fun equalTo(other: Creator): Boolean = other === this
    }

    /**
     * Internal dummy value used when TicketManager is unable to find an accurate User or Console object
     */
    object UUIDNoMatch : Creator {
        override infix fun equalTo(other: Creator): Boolean = other === this
    }

    /**
     * Ticket type is used for types which contain an instance of TicketCreator but is not
     * applicable. For example, events fired contain the ticket creator, but the mass-close command
     * targets many tickets. Thus, a dummy creator is used.
     */
    object DummyCreator : Creator {
        override infix fun equalTo(other: Creator): Boolean = other === this
    }
}