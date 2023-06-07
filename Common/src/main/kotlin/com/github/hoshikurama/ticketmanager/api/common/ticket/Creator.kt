package com.github.hoshikurama.ticketmanager.api.common.ticket

import java.util.*

/**
 * Represents, identifies, and compares entities which can create or modify a ticket. It is strictly used for
 * ticket action purposes.
 */
sealed interface Creator {

    /**
     * Normal player on a Ticket/Action
     * @property uuid Player's unique ID on the server/network.
     */
    class User(val uuid: UUID) : Creator {
        override fun equals(other: Any?): Boolean = other != null && other is User && this.uuid == other.uuid
        override fun hashCode(): Int = uuid.hashCode()
    }

    /**
     * Console on a Ticket/Action
     */
    object Console : Creator {
        override fun equals(other: Any?): Boolean = other === this
    }

    /**
     * Internal dummy value used when TicketManager is unable to find an accurate User or Console object
     */
    @Suppress("Unused")
    object UUIDNoMatch : Creator {
        override fun equals(other: Any?): Boolean = other === this
    }

    /**
     * Ticket type is used for types which contain an instance of TicketCreator but is not
     * applicable. For example, events fired contain the ticket creator, but the mass-close command
     * targets many tickets. Thus, a dummy creator is used.
     */
    @Suppress("Unused")
    object DummyCreator : Creator {
        override fun equals(other: Any?): Boolean = other === this
    }
}