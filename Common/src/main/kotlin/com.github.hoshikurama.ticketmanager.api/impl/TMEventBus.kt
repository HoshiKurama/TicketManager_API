package com.github.hoshikurama.ticketmanager.api.impl

import com.github.hoshikurama.ticketmanager.api.events.*
import com.github.hoshikurama.tmcoroutine.TMCoroutine
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.full.isSupertypeOf
import kotlin.reflect.typeOf

private typealias TMEL<T> = TMEventBus.Internal.EventListener<T>

/**
 * This event bus distributes TicketManager events of supertype [TMEvent] to subscribed listeners registered via the [subscribe] function. These
 * listeners begin execution asynchronously in an off-thread suspendable coroutine. Registration and deregistration are
 * thread-safe.
 *
 * @property internal encapsulates exposed internals because [subscribe] is reified (for awesome syntax). Developers should not access this property
 * unless absolutely necessary.
 */
class TMEventBus {
    val internal = Internal()

    /**
     * Registers a [listener] that will execute when [Event] is either: (1) a supertype of, or (2) the same type as the
     * fired event. The function is reified to allow for an awesome syntax. See [TMEvent] for more information about the
     * event bus.
     *
     * @return function that unregisters [listener] when invoked.
     */
    @Suppress("UNCHECKED_CAST", "MemberVisibilityCanBePrivate")
    inline fun <reified Event : TMEvent> subscribe(noinline listener: suspend (Event) -> Unit): () -> Unit {
        val eventListener = Internal.EventListener(listener)
        val eventType = typeOf<Event>()
        val uuid = UUID.randomUUID()

        if (eventType.isSupertypeOf(typeOf<TicketCreateEvent>()))
            internal.ticketCreate[uuid] = eventListener as TMEL<TicketCreateEvent>
        if (eventType.isSupertypeOf(typeOf<TicketAssignEvent>()))
            internal.ticketAssign[uuid] = eventListener as TMEL<TicketAssignEvent>
        if (eventType.isSupertypeOf(typeOf<TicketReopenEvent>()))
            internal.ticketReopen[uuid] = eventListener as TMEL<TicketReopenEvent>
        if (eventType.isSupertypeOf(typeOf<TicketCommentEvent>()))
            internal.ticketComment[uuid] = eventListener as TMEL<TicketCommentEvent>
        if (eventType.isSupertypeOf(typeOf<TicketMassCloseEvent>()))
            internal.ticketMassClose[uuid] = eventListener as TMEL<TicketMassCloseEvent>
        if (eventType.isSupertypeOf(typeOf<TicketSetPriorityEvent>()))
            internal.ticketSetPriority[uuid] = eventListener as TMEL<TicketSetPriorityEvent>
        if (eventType.isSupertypeOf(typeOf<TicketCloseWithCommentEvent>()))
            internal.ticketCloseWithComment[uuid] = eventListener as TMEL<TicketCloseWithCommentEvent>
        if (eventType.isSupertypeOf(typeOf<TicketCloseWithoutCommentEvent>()))
            internal.ticketCloseWithoutComment[uuid] = eventListener as TMEL<TicketCloseWithoutCommentEvent>
        if (eventType.isSupertypeOf(typeOf<TicketReadReceiptEvent>()))
            internal.ticketReadReceipt[uuid] = eventListener as TMEL<TicketReadReceiptEvent>

        return { internal.allEvents.forEach { it.remove(uuid) } }
    }

    // Anything past this point is internal and does not concern end-users

    /**
     * Contains internal variables only exposed because Java has some weird fetish with type erasure. Kotlin reified is
     * the workaround but requires I expose everything.
     */
    class Internal {
        // Ticket events
        val ticketCreate = ConcurrentHashMap<UUID, TMEL<TicketCreateEvent>>()
        val ticketAssign = ConcurrentHashMap<UUID, TMEL<TicketAssignEvent>>()
        val ticketReopen = ConcurrentHashMap<UUID, TMEL<TicketReopenEvent>>()
        val ticketComment = ConcurrentHashMap<UUID, TMEL<TicketCommentEvent>>()
        val ticketMassClose = ConcurrentHashMap<UUID, TMEL<TicketMassCloseEvent>>()
        val ticketSetPriority = ConcurrentHashMap<UUID, TMEL<TicketSetPriorityEvent>>()
        val ticketCloseWithComment = ConcurrentHashMap<UUID, TMEL<TicketCloseWithCommentEvent>>()
        val ticketCloseWithoutComment = ConcurrentHashMap<UUID, TMEL<TicketCloseWithoutCommentEvent>>()
        val ticketReadReceipt = ConcurrentHashMap<UUID, TMEL<TicketReadReceiptEvent>>()

        val allEvents = listOf(ticketCreate, ticketAssign, ticketReopen, ticketComment, ticketMassClose,
            ticketSetPriority, ticketCloseWithComment, ticketCloseWithoutComment, ticketReadReceipt,
        )

        /**
         * Internally used to store events in such a way to allow contravariance
         */
        class EventListener<in Event: TMEvent>(private val lambda: suspend (Event) -> Unit) {
            suspend fun onEvent(event: Event) = lambda.invoke(event)
        }

        @Suppress("Unused")
        /**
         * Internally used by TicketManager to call events.
         */
        suspend fun callAsync(event: TMEvent): Unit = when (event) {
            is TicketCreateEvent -> ticketCreate.execute(event)
            is TicketCommentEvent -> ticketComment.execute(event)
            is TicketAssignEvent -> ticketAssign.execute(event)
            is TicketReopenEvent -> ticketReopen.execute(event)
            is TicketCloseWithCommentEvent -> ticketCloseWithComment.execute(event)
            is TicketCloseWithoutCommentEvent -> ticketCloseWithoutComment.execute(event)
            is TicketSetPriorityEvent -> ticketSetPriority.execute(event)
            is TicketMassCloseEvent -> ticketMassClose.execute(event)
            is TicketReadReceiptEvent -> ticketReadReceipt.execute(event)
        }
    }
}

private suspend fun <Event : TMEvent> ConcurrentHashMap<UUID, TMEL<Event>>.execute(event: Event) {
    this.values.forEach { subscriber ->
        TMCoroutine.Supervised.launch {
            safeExecute { subscriber.onEvent(event) }
        }
    }
}

private inline fun safeExecute(f: () -> Unit) {
    try { f() }
    catch (e: Exception) { e.printStackTrace() }
}