package com.github.hoshikurama.ticketmanager.api.ticket.impl

import com.github.hoshikurama.ticketmanager.api.commands.CommandSender
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket
import java.util.*
import com.github.hoshikurama.ticketmanager.api.ticket.impl.Ticket as TicketImpl

/**
 * Default implementation details for CommandSender
 */
object CommandSender {

    object Info {
        /**
         * Default implementation for Info.Player
         */
        open class Player(
            override val username: String,
            override val uuid: UUID,
        ) : CommandSender.Info.Player {
            final override fun asInfoString() = "CSI_USER.$username.$uuid"
            final override fun asCreator(): Ticket.Creator = TicketImpl.Creator.User(uuid)
        }

        /**
         * Default implementation for Info.Console
         */
        open class Console : CommandSender.Info.Console {
            final override fun asInfoString() = "CSI_CONSOLE"
            final override fun asCreator(): Ticket.Creator = TicketImpl.Creator.Console
        }
    }

    object Active {
        /**
         * Default implementation for Active.Player
         */
        abstract class OnlinePlayer(username: String, uuid: UUID) : CommandSender.Active.OnlinePlayer, Info.Player(username, uuid)

        /**
         * Default implementation for Active.Console
         */
        abstract class OnlineConsole : CommandSender.Active.OnlineConsole, Info.Console()
    }
}