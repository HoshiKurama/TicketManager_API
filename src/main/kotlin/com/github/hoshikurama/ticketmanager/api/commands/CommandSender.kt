package com.github.hoshikurama.ticketmanager.api.commands

import com.github.hoshikurama.ticketmanager.api.ticket.ActionLocation
import com.github.hoshikurama.ticketmanager.api.ticket.Assignment
import com.github.hoshikurama.ticketmanager.api.ticket.Creator
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.luckperms.api.LuckPermsProvider
import java.util.*

/**
 * Abstraction for anything in TicketManager that either has or is executing a command.
 */
sealed interface CommandSender {

    /**
     * Converts a command sender into a ticket creator.
     */
    fun asCreator(): Creator

    /**
     * Converts a command sender into a ticket assignment
     */
    fun asAssignment(): Assignment


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
        open class Player(
            val username: String,
            val uuid: UUID,
        ) : Info {
            final override fun asInfoString() = "CSI_USER.$username.$uuid"
            final override fun asCreator(): Creator = Creator.User(uuid)
            final override fun asAssignment(): Assignment = Assignment.Player(username)
        }

        /**
         * Represents a server-agnostic Console command sender. Complementary Active type is OnlineConsole.
         * @see CommandSender.Active.OnlineConsole
         */
        open class Console : Info {
            final override fun asInfoString() = "CSI_CONSOLE"
            final override fun asCreator(): Creator = Creator.Console
            final override fun asAssignment(): Assignment = Assignment.Console
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
        val serverName: String?
        /**
         * Acquire the location of the player as a TicketCreationLocation.
         */
        fun getLocAsTicketLoc(): ActionLocation

        /**
         * Send a chat message to the sender.
         * @param msg Message which is first parsed as a Kyori MiniMessage.
         *
         */
        @Suppress("Unused")
        fun sendMessage(msg: String) = sendMessage(MiniMessage.miniMessage().deserialize(msg))

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
        abstract class OnlinePlayer(
            username: String,
            uuid: UUID,
        ) : Active, Info.Player(username, uuid) {
            private val lpUser = LuckPermsProvider
                .get()
                .userManager
                .getUser(uuid)!!
            @Suppress("Unused")
            val permissionGroups: List<String> = lpUser
                .getInheritedGroups(lpUser.queryOptions)
                .map { it.name }

            final override fun has(permission: String): Boolean = lpUser.cachedData
                .permissionData
                .checkPermission(permission)
                .asBoolean()
        }

        /**
         * Represents Console with an active internal connection to the server.
         */
        abstract class OnlineConsole(override val serverName: String) : Active, Info.Console() {
            final override fun getLocAsTicketLoc(): ActionLocation = ActionLocation.FromConsole(serverName)
            final override fun has(permission: String): Boolean = true
        }
    }
}