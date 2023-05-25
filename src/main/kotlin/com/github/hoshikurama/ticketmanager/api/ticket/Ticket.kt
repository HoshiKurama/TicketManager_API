package com.github.hoshikurama.ticketmanager.api.ticket

//TODO THIS IS SUPER FUCKED
import java.util.UUID
/**
 * Tickets are the foundation of TicketManager; they are both immutable and thread-safe.
 * @property id Unique ID the ticket has been assigned
 * @property creator Ticket creator
 * @property priority Priority level
 * @property status Status (open/closed)
 * @property assignedTo Ticket assignment.
 * @property creatorStatusUpdate Used internally to indicate if the creator has seen the last change to their ticket.
 * @property actions Chronological list of modifications made to the initial ticket.
 */
interface Ticket<out GActions, out GActionType, out GCreator, out GAssignment>
        where GActionType : Ticket.Action.Type,
              GAssignment : Ticket.Assignment,
              GActions : Ticket.Action<*,*,*>,
              GCreator : Ticket.Creator
{
    val id: Long
    val creator: GCreator
    val priority: Priority
    val status: Status
    val assignedTo: GAssignment
    val creatorStatusUpdate: Boolean
    val actions: List<GActions>

    /**
     * Encapsulates the priority level of a ticket.
     */
    enum class Priority {
        LOWEST, LOW, NORMAL, HIGH, HIGHEST
    }

    /**
     * Encapsulates the status of a ticket, which is either open or closed
     */
    enum class Status {
        OPEN, CLOSED;
    }


    /**
     * Represents any action done upon a ticket, including creation. Actions are immutable and thread-safe.
     * @property type contains the action type and any data associated with that particular action type.
     * @property user user who performed the action on the ticket
     * @property location location where user performed the action on the ticket
     * @property timestamp Epoch time for when the action was performed
     */
    interface Action<out GActionType, out GCreator, out GCreationLocation>
            where GCreationLocation : CreationLocation,
                  GActionType : Action.Type,
                  GCreator : Creator
    {
        val type: GActionType
        val user: GCreator
        val location: GCreationLocation
        val timestamp: Long

        /**
         * All actions have a type. Some types carry additional relevant information. All implementors of Type represent
         * all different types of Ticket modifications.
         */
        interface Type {
            /**
             * Ticket assignment action
             */
            interface Assign : Type {
                val assignment: Assignment
            }

            /**
             * Ticket comment action
             */
            interface Comment : Type {
                val comment: String
            }

            /**
             * Closes a ticket while also leaving a comment.
             */
            interface CloseWithComment : Type {
                val comment: String
            }

            /**
             * Opening ticket. Note: the initial message is contained here.
             */
            interface Open : Type {
                val message: String
            }

            /**
             * Priority-Change ticket action.
             */
            interface SetPriority : Type {
                val priority: Priority
            }

            /**
             * Closing ticket action. Note: This is a pure close.
             */
            interface CloseWithoutComment : Type

            /**
             * Re-open ticket action.
             */
            interface Reopen : Type

            /**
             * Mass-Close ticket action.
             */
            interface MassClose : Type
        }
    }


    /**
     * Represents the 3 distinctions TicketManager makes for ticket assignments:
     * - Console
     * - Nobody
     * - Other (Players, Permission Groups, & Phrases)
     *
     * Developers should not implement this interface as the behaviour is not overridable.
     * Please use it to facilitate your decision in how you store ticket assignments.
     */
    interface Assignment {
            /**
             * Represents no assignment.
             */
        interface Nobody : Assignment
        /**
         * Represents Console
         */
        interface Console : Assignment
        /**
         * Represents anything other than Nobody or Console. More specifically, this will be a phrase assignment,
         * which may or may not represent a player or permission group.
         */
        interface Other : Assignment {
            val assignment: String
        }
    }


    /**
     * Creation location of a ticket. Nullable values account for all creator types.
     * @property server specified by the plugin configuration file (for proxy networks)
     * @see FromPlayer
     * @see FromConsole
     */
    interface CreationLocation {
        val server: String?

       /**
        * Denotes the creation location of a player ticket. All values are not null except for the following:
        * @property world world ticket is created in
        * @property x x-block position
        * @property y y-block position
        * @property z z-block position
        */
       interface FromPlayer : CreationLocation {
           val world: String
           val x: Int
           val y: Int
           val z: Int
       }

       /**
        * Denotes the creation location of a console ticket. Values should always be null except for the server
        * if in proxy mode
        */
       interface FromConsole : CreationLocation
    }


    /**
     * Abstractly represents, identifies, and compares entities which can create or modify a ticket. It is strictly used for
     * ticket action purposes.
     * @see User
     * @see Console
     */
    interface Creator {
        /**
         * Normal player on a Ticket/Action
         * @property uuid Player's unique ID on the server/network.
         */
        interface User : Creator {
            val uuid: UUID
        }

        /**
         * Console on a Ticket/Action
         */
        interface Console : Creator

        /**
         * Internal dummy value used when TicketManager is unable to find an accurate User or Console object
         */
        interface UUIDNoMatch : Creator

        /**
         * Ticket type is used for types which contain an instance of TicketCreator but is not
         * applicable. For example, events fired contain the ticket creator, but the mass-close command
         * targets many tickets. Thus, a dummy creator is used.
         */
        interface DummyCreator : Creator
    }
}