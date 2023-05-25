package com.github.hoshikurama.ticketmanager.api.ticket.impl

import com.github.hoshikurama.ticketmanager.api.commands.CommandSender
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Priority
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Status
import java.time.Instant
import java.util.UUID
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket as TicketAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action as ActionAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.Type as ActionTypeAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.Type.Assign as AssignAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.Type.CloseWithComment as CloseWithCommentAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.Type.Comment as CommentAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.Type.Open as OpenAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.Type.CloseWithoutComment as CloseWithoutCommentAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.Type.MassClose as MassCloseAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.Type.Reopen as ReopenAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action.Type.SetPriority as SetPriorityAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Assignment as AssignmentAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Assignment.Console as AssignConsoleAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Assignment.Nobody as AssignNobodyAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Assignment.Other as AssignOtherAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.CreationLocation as CreationLocationAPI
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
    override val creator: Creator,
    override val priority: Priority = Priority.NORMAL,
    override val status: Status = Status.OPEN,
    override val assignedTo: Assignment = Assignment.Nobody,
    override val creatorStatusUpdate: Boolean = false,
    override val actions: List<Action> = listOf()
) : TicketAPI<Ticket.Action, Ticket.Action.Type, Ticket.Creator, Ticket.Assignment> {

    operator fun plus(actions: List<Action>): Ticket {
        return Ticket(id, creator, priority, status, assignedTo, creatorStatusUpdate, actions)
    }

    operator fun plus(action: Action): Ticket {
        return Ticket(id, creator, priority, status, assignedTo, creatorStatusUpdate, this.actions + action)
    }


    class Action(
        override val type: Type,
        override val user: Creator,
        override val location: CreationLocation,
        override val timestamp: Long = Instant.now().epochSecond
    ) : ActionAPI<Action.Type, Creator, CreationLocation> {

        constructor(type: Type, activeSender: CommandSender.Active): this(
            type = type,
            user = activeSender.asCreator(),
            location = activeSender.getLocAsTicketLoc(),
        )

        sealed interface Type : ActionTypeAPI
        class Assign(override val assignment: Assignment) : AssignAPI, Type
        class Comment(override val comment: String) : CommentAPI, Type
        class CloseWithComment(override val comment: String) : CloseWithCommentAPI, Type
        class Open(override val message: String): OpenAPI, Type
        class SetPriority(override val priority: Priority) : SetPriorityAPI, Type
        object CloseWithoutComment : CloseWithoutCommentAPI, Type
        object Reopen : ReopenAPI, Type
        object MassClose : MassCloseAPI, Type

        enum class TypeAsEnum {
            ASSIGN, CLOSE, CLOSE_WITH_COMMENT, COMMENT, OPEN, REOPEN, SET_PRIORITY, MASS_CLOSE
        }
    }


    sealed interface Assignment : AssignmentAPI {
        object Nobody : Assignment, AssignNobodyAPI
        object Console : Assignment, AssignConsoleAPI
        class Other(override val assignment: String): Assignment, AssignOtherAPI
    }


    sealed interface CreationLocation : CreationLocationAPI {

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


    sealed interface Creator : CreatorAPI {
        class User(override val uuid: UUID) : Creator, CreatorUserAPI
        object DummyCreator : Creator, CreatorDummyCreatorAPI
        object UUIDNoMatch : Creator, CreatorUUIDNoMatchAPI
        object Console : Creator, CreatorConsoleAPI
    }
}


infix fun Ticket.Assignment.equalTo(other: Ticket.Assignment) = when (this) {
    is Ticket.Assignment.Nobody, is Ticket.Assignment.Console -> this === other
    is Ticket.Assignment.Other -> other is Ticket.Assignment.Other && this.assignment == other.assignment
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

fun Ticket.Creator.User.equalTo(other: Ticket.Creator) =
    (other as? Ticket.Creator.User)?.uuid?.equals(this.uuid) ?: false

/*
fun CommandSender.Info.asCreator(): com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Creator = when (this) {
    is CommandSender.Active.OnlineConsole,
    is CommandSender.Info.Console ->
    is CommandSender.Active.OnlinePlayer,
    is CommandSender.Info.Player ->
}