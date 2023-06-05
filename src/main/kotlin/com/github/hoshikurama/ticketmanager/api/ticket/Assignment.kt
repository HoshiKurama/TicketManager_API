package com.github.hoshikurama.ticketmanager.api.ticket

/**
 * Represents a ticket assignment. Below are the current types:
 * - Console
 * - Nobody
 * - Player
 * - Permission Group
 * - Phrase
 */
sealed interface Assignment {
    /**
     * Compares two assignments
     */
    infix fun equalTo(other: Assignment): Boolean

    /**
     * Represents no assignment
     */
    @Suppress("Unused")
    object Nobody : Assignment {
       override infix fun equalTo(other: Assignment): Boolean = this === other
    }

    /**
     * Represents Console
     */
    object Console : Assignment {
        override infix fun equalTo(other: Assignment): Boolean = this === other
    }

    /**
     * Represents one player
     */
    class Player(val username: String) : Assignment {
        override infix fun equalTo(other: Assignment): Boolean = other is Player && other.username == this.username
    }

    /**
     * Represents a permission group
     */
    @Suppress("Unused")
    class PermissionGroup(val permissionGroup: String) : Assignment {
        override infix fun equalTo(other: Assignment): Boolean = other is PermissionGroup && other.permissionGroup == this.permissionGroup
    }

    /**
     * Represents a phrase
     */
    @Suppress("Unused")
    class Phrase(val phrase: String) : Assignment {
        override infix fun equalTo(other: Assignment): Boolean = other is Phrase && other.phrase == this.phrase
    }
}