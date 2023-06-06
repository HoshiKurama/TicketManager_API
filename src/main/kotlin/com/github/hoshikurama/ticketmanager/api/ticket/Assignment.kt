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
     * Represents no assignment
     */
    @Suppress("Unused")
    object Nobody : Assignment

    /**
     * Represents Console
     */
    object Console : Assignment

    /**
     * Represents one player
     */
    class Player(val username: String) : Assignment {
        override fun equals(other: Any?): Boolean = other != null && other is Player && other.username == this.username
        override fun hashCode(): Int = username.hashCode()
    }

    /**
     * Represents a permission group
     */
    @Suppress("Unused")
    class PermissionGroup(val permissionGroup: String) : Assignment, Any() {
        override fun equals(other: Any?): Boolean = other != null && other is PermissionGroup && other.permissionGroup == this.permissionGroup
        override fun hashCode(): Int = permissionGroup.hashCode()
    }

    /**
     * Represents a phrase
     */
    @Suppress("Unused")
    class Phrase(val phrase: String) : Assignment, Any() {
        override fun equals(other: Any?): Boolean = other != null && other is Phrase && other.phrase == this.phrase
        override fun hashCode(): Int = phrase.hashCode()
    }
}