package com.github.hoshikurama.ticketmanager.api.ticket.impl

import com.github.hoshikurama.ticketmanager.api.commands.CommandSender
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Priority
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Status
import java.time.Instant
import java.util.UUID
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket as TicketAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action as ActionAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.Type as ActionTypeAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.Assign as AssignAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.CloseWithComment as CloseWithCommentAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.Comment as CommentAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.Open as OpenAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.CloseWithoutComment as CloseWithoutCommentAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.MassClose as MassCloseAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.Reopen as ReopenAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.SetPriority as SetPriorityAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Assignment as AssignmentAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Assignment.Console as AssignConsoleAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Assignment.Nobody as AssignNobodyAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Assignment.Other as AssignOtherAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.CreationLocation.FromConsole as FromConsoleAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.CreationLocation.FromPlayer as FromPlayerAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Creator as CreatorAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Creator.Console as CreatorConsoleAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Creator.DummyCreator as CreatorDummyCreatorAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Creator.UUIDNoMatch as CreatorUUIDNoMatchAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Creator.User as CreatorUserAPI

/**
 * This is the default implementation for Tickets in TicketManager
 */
class Ticket(
    override val id: Long = -1L,
    override val creator: CreatorAPI,
    override val priority: Priority = Priority.NORMAL,
    override val status: Status = Status.OPEN,
    override val assignedTo: AssignmentAPI = Assignment.Nobody,
    override val creatorStatusUpdate: Boolean = false,
    override val actions: List<Action> = listOf()
) : TicketAPI {

    operator fun plus(actions: List<Action>): Ticket {
        return Ticket(id, creator, priority, status, assignedTo, creatorStatusUpdate, actions)
    }

    operator fun plus(action: Action): Ticket {
        return Ticket(id, creator, priority, status, assignedTo, creatorStatusUpdate, this.actions + action)
    }


    class Action(
        override val type: ActionTypeAPI,
        override val user: CreatorAPI,
        override val location: TicketAPI.CreationLocation,
        override val timestamp: Long = Instant.now().epochSecond
    ) : ActionAPI {

        constructor(type: ActionTypeAPI, activeSender: CommandSender.Active): this(
            type = type,
            user = activeSender.asCreator(),
            location = activeSender.getLocAsTicketLoc(),
        )

        sealed interface Type
        class Comment(override val comment: String) : CommentAPI, Type
        class Assign(override val assignment: AssignmentAPI) : AssignAPI, Type
        class CloseWithComment(override val comment: String) : CloseWithCommentAPI, Type
        class SetPriority(override val priority: Priority) : SetPriorityAPI, Type
        class Open(override val message: String): OpenAPI, Type
        object CloseWithoutComment : CloseWithoutCommentAPI, Type
        object MassClose : MassCloseAPI, Type
        object Reopen : ReopenAPI, Type

        enum class TypeAsEnum {
            ASSIGN, CLOSE, CLOSE_WITH_COMMENT, COMMENT, OPEN, REOPEN, SET_PRIORITY, MASS_CLOSE
        }
    }


    sealed interface Assignment {
        object Nobody : Assignment, AssignNobodyAPI
        object Console : Assignment, AssignConsoleAPI
        class Other(override val assignment: String): Assignment, AssignOtherAPI
    }


    sealed interface CreationLocation {

        @JvmRecord
        data class FromPlayer(
            override val server: String?,
            override val world: String,
            override val x: Int,
            override val y: Int,
            override val z: Int,
        ) : CreationLocation, FromPlayerAPI {
            override fun toString(): String = "${server ?: ""} $world $x $y $z".trimStart()
        }

        @JvmRecord
        data class FromConsole(
            override val server: String?
        ) : CreationLocation, FromConsoleAPI {
            override fun toString(): String = "${server ?: ""} ${""} ${""} ${""} ${""}".trimStart()
        }
    }


    sealed interface Creator {
        class User(override val uuid: UUID) : Creator, CreatorUserAPI
        object DummyCreator : Creator, CreatorDummyCreatorAPI
        object UUIDNoMatch : Creator, CreatorUUIDNoMatchAPI
        object Console : Creator, CreatorConsoleAPI
    }
}


fun Ticket.Action.Type.getEnum() = when (this) {
    is Ticket.Action.Open -> Ticket.Action.TypeAsEnum.OPEN
    is Ticket.Action.Reopen -> Ticket.Action.TypeAsEnum.REOPEN
    is Ticket.Action.Assign -> Ticket.Action.TypeAsEnum.ASSIGN
    is Ticket.Action.Comment -> Ticket.Action.TypeAsEnum.COMMENT
    is Ticket.Action.MassClose -> Ticket.Action.TypeAsEnum.MASS_CLOSE
    is Ticket.Action.SetPriority -> Ticket.Action.TypeAsEnum.SET_PRIORITY
    is Ticket.Action.CloseWithoutComment -> Ticket.Action.TypeAsEnum.CLOSE
    is Ticket.Action.CloseWithComment -> Ticket.Action.TypeAsEnum.CLOSE_WITH_COMMENT
}