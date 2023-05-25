package com.github.hoshikurama.ticketmanager.api.commands

import com.github.hoshikurama.ticketmanager.api.ticket.Ticket
import net.kyori.adventure.text.Component
import java.util.*

/**
 * Anything in TicketManager that either has or is executing a command.
 *
 * If you wish to use your own implementation, then you MUST have both an Active type and Info type per implemented concept.
 * For example, the default implementation for players is Info.Player and Active.OnlinePlayer.
 *
 * More information about the behaviours for Active and Info types can be found in the respective documentation.
 */
sealed interface CommandSender {
    /**
     * Holds basic information about a known command origin. This type is internally used to
     * represent a server-agnostic command sender and thus able to be sent across a proxy when converted to its
     * String representation.
     *
     * Instances should contain enough information to rebuild a CommandSender.Active instance on a server.
     */
    interface Info : CommandSender {
        /**
         * Creates a String representation useful for sending in a message channel.
         * Please note that the implementations are final.
         */
        fun asInfoString(): String

        /**
         * Represents a server-agnostic player command sender via their username and UUID.
         * Complementary Active type is OnlinePlayer
         * @see CommandSender.Type.Active.OnlinePlayer
         */
        interface Player : Info {
            val username: String
            val uuid: UUID

        }
    }
}

/*
sealed interface CommandSender {

    sealed interface Info : CommandSender {

        /**
         * Represents a server-agnostic player command sender via their username and UUID.
         * Complementary Active type is OnlinePlayer
         * @see CommandSender.Active.OnlinePlayer
         */
        open class Player(val username: String, val uuid: UUID) : Info {
            final override fun asInfoString() = "CSI_USER.$username.$uuid"
        }

        /**
         * Represents a server-agnostic Console command sender. Complementary Active type is OnlineConsole.
         * @see CommandSender.Active.OnlineConsole
         */
        open class Console : Info {
            final override fun asInfoString() = "CSI_CONSOLE"
        }
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
        abstract class OnlinePlayer(username: String, uuid: UUID) : Active, Info.Player(username, uuid)

        /**
         * Represents Console with an active internal connection to the server.
         */
        abstract class OnlineConsole : Active, Info.Console()
    }
}