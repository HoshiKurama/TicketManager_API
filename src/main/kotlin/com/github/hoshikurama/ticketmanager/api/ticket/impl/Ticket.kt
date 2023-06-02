package com.github.hoshikurama.ticketmanager.api.ticket.impl

import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Priority
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Status
import java.time.Instant
import java.util.UUID
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket as TicketAPI
import com.github.hoshikurama.ticketmanager.api.ticket.Ticket.Action as ActionAPI
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
class Ticket<out GCreator, out GAssignment, out GActionType, out GCreationLoc>(
    override val id: Long,
    override val creator: GCreator,
    override val priority: Priority,
    override val status: Status,
    override val assignedTo: GAssignment,
    override val creatorStatusUpdate: Boolean,
    override val actions: List<Action<GActionType, GCreator, GCreationLoc>>,
) : TicketAPI<GCreator, GAssignment, GActionType, GCreationLoc>
        where GCreator : Ticket.Creator,
              GCreator : CreatorAPI,
              GAssignment : Ticket.Assignment,
              GAssignment : AssignmentAPI,
              GActionType : Ticket.Action.Type,
              GActionType : TicketAPI.Action.Type,
              GCreationLoc : Ticket.CreationLocation,
              GCreationLoc : TicketAPI.CreationLocation
{

    operator fun plus(actions: List<Action<*, *, *>>): Ticket<*, GAssignment, *, *> {
        return Ticket(id, creator, priority, status, assignedTo, creatorStatusUpdate, actions)
    }

    operator fun plus(action: Action<*,*,*>): Ticket<*, GAssignment, *, *> {
        return Ticket(id, creator, priority, status, assignedTo, creatorStatusUpdate, this.actions + action)
    }


    class Action<out GType, out GCreator, out GCreationLoc>(
        override val type: GType,
        override val user: GCreator,
        override val location: GCreationLoc,
        override val timestamp: Long = Instant.now().epochSecond
    ) : ActionAPI<GType, GCreator, GCreationLoc>
            where GType : Action.Type,
                  GType : TicketAPI.Action.Type,
                  GCreator : Creator,
                  GCreator : TicketAPI.Creator,
                  GCreationLoc : CreationLocation,
                  GCreationLoc : TicketAPI.CreationLocation
    {

        sealed interface Type
        class Comment(override val comment: String) : TicketAPI.Action.Comment, Type
        class Assign(override val assignment: AssignmentAPI) : TicketAPI.Action.Assign, Type
        class CloseWithComment(override val comment: String) : TicketAPI.Action.CloseWithComment, Type
        class SetPriority(override val priority: Priority) : TicketAPI.Action.SetPriority, Type
        class Open(override val message: String): TicketAPI.Action.Open, Type
        object CloseWithoutComment : TicketAPI.Action.CloseWithoutComment, Type
        object MassClose : TicketAPI.Action.MassClose, Type
        object Reopen : TicketAPI.Action.Reopen, Type
    }


    sealed class Assignment {
        infix fun equalTo(other: TicketAPI.Assignment): Boolean = when {
            (this is TicketAPI.Assignment.Other) && (other is TicketAPI.Assignment.Other) ->
                this.assignment == other.assignment
            else -> this === other
        }

        object Nobody : Assignment(), AssignNobodyAPI
        object Console : Assignment(), AssignConsoleAPI
        class Other(override val assignment: String): Assignment(), AssignOtherAPI
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


    sealed class Creator {
        infix fun equalTo(other: CreatorAPI): Boolean = when (this) {
            is User -> other is CreatorUserAPI && this.uuid == other.uuid
            is DummyCreator -> other is CreatorDummyCreatorAPI
            is UUIDNoMatch -> other is CreatorUUIDNoMatchAPI
            is Console -> other is CreatorConsoleAPI
        }

        class User(override val uuid: UUID) : Creator(), CreatorUserAPI
        object DummyCreator : Creator(), CreatorDummyCreatorAPI
        object UUIDNoMatch : Creator(), CreatorUUIDNoMatchAPI
        object Console : Creator(), CreatorConsoleAPI
    }
}

/*
//TODO UHH WHAT? THIS IS IMPL SPECIFIC FOR DATABASE

enum class TypeAsEnum {
    ASSIGN, CLOSE, CLOSE_WITH_COMMENT, COMMENT, OPEN, REOPEN, SET_PRIORITY, MASS_CLOSE
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
 */