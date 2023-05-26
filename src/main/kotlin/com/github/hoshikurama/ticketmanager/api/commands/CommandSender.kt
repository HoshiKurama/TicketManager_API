package com.github.hoshikurama.ticketmanager.api.commands

import com.github.hoshikurama.ticketmanager.api.ticket.Ticket
import net.kyori.adventure.text.Component
import java.util.*

/**
 * Abstraction for anything in TicketManager that either has or is executing a command.
 */
sealed interface CommandSender {
    /**
     * Converts a command sender into a ticket creator.
     */
    fun asCreator(): Ticket.Creator
    /**
     * Holds basic information about a known command origin. This type is internally used to
     * represent a server-agnostic command sender and thus able to be sent across a proxy when converted to its
     * String representation.
     *
     * Instances should contain enough information to rebuild a CommandSender.Active instance on a server.
     */
    sealed interface Info : CommandSender {
        /**
         * Converts the Info implementor into a string form which can be sent across message channels.
         */
        fun asInfoString(): String

        /**
         * Represents a server-agnostic player command sender via their username and UUID.
         * Complementary Active type is OnlinePlayer
         * @see CommandSender.Active.OnlinePlayer
         */
        interface Player : Info {
            val username: String
            val uuid: UUID
        }

        /**
         * Represents a server-agnostic Console command sender. Complementary Active type is OnlineConsole.
         * @see CommandSender.Active.OnlineConsole
         */
        interface Console : Info
    }


    /**
     * Holds information about a known command origin. However, implementors internally contain an active
     * reference to the origin, which allows for the following functionality:
     * - Send messages to the sender
     * - Check the sender's permissions
     *
     * Each sub-type also implements from its complementary CommandSender.Info sub-type. Calling the
     * #asInfoString() function will not retain any information which is not already included by the
     * complementary Info representation.
     */
    sealed interface Active : Info, CommandSender {

        /**
         * Acquire the location of the player as a TicketCreationLocation.
         */
        fun getLocAsTicketLoc(): Ticket.CreationLocation

        /**
         * Send a chat message to the sender.
         * @param msg Message which is first parsed as a Kyori MiniMessage.
         *
         */
        fun sendMessage(msg: String)

        /**
         * Sends a chat message to the sender.
         * @param component Kyori Adventure API component to send
         */
        fun sendMessage(component: Component)

        /**
         * Checks if a command sender has a particular permission.
         * @param permission permission node
         */
        fun has(permission: String): Boolean


        /**
         * Represents a Player with an active internal connection to the server.
         */
        interface OnlinePlayer : Active, Info.Player

        /**
         * Represents Console with an active internal connection to the server.
         */
        interface OnlineConsole : Active, Info.Console
    }
}