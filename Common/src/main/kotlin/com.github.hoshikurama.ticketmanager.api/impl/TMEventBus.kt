package com.github.hoshikurama.ticketmanager.api.impl

import com.github.hoshikurama.ticketmanager.api.events.*
import com.github.hoshikurama.tmcoroutine.TMCoroutine
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

private typealias TMEL<T> = TMEventBus.Internal.EventListener<T>

/**
 * The TMEventBus distributes all TicketManager events to any subscribed listeners in a thread-safe manner. To initiate a listener, call
 * the #subscribe() function with the event type. Please note:
 * - Event must be a subtype of TMEvent.
 * - The listener is also called for any supertype listeners. For example, TicketEvent receives all ticket-related events.
 * - New subscriptions can be created or destroyed on any thread and at any time.
 *
 * NOTE: TMEventBus differs from other registration points as it exposes all internals. This is necessary as #subscribe()
 * is reified to provide developers with an awesome syntax. All internals are encapsulated by the internal property to
 * prevent accidental usage. Please don't use internals unless you have a very good reason for doing so.
 */
class TMEventBus {
    val internal = Internal()

    /**
     *  Allows users to subscribe to TicketManager events. This can be called at any time and on any thread.
     *  @return a function to unregister your listener
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <reified Event : TMEvent> subscribe(noinline listener: suspend (Event) -> Unit): () -> Unit {
        val eventListener = Internal.EventListener(listener)
        val uuid = UUID.randomUUID()

        if (Event::class.isAssignableFrom(TicketCreateEvent::class))
            internal.ticketCreate[uuid] = eventListener as TMEL<TicketCreateEvent>
        if (Event::class.isAssignableFrom(TicketAssignEvent::class))
            internal.ticketAssign[uuid] = eventListener as TMEL<TicketAssignEvent>
        if (Event::class.isAssignableFrom(TicketReopenEvent::class))
            internal.ticketReopen[uuid] = eventListener as TMEL<TicketReopenEvent>
        if (Event::class.isAssignableFrom(TicketCommentEvent::class))
            internal.ticketComment[uuid] = eventListener as TMEL<TicketCommentEvent>
        if (Event::class.isAssignableFrom(TicketMassCloseEvent::class))
            internal.ticketMassClose[uuid] = eventListener as TMEL<TicketMassCloseEvent>
        if (Event::class.isAssignableFrom(TicketSetPriorityEvent::class))
            internal.ticketSetPriority[uuid] = eventListener as TMEL<TicketSetPriorityEvent>
        if (Event::class.isAssignableFrom(TicketCloseWithCommentEvent::class))
            internal.ticketCloseWithComment[uuid] = eventListener as TMEL<TicketCloseWithCommentEvent>
        if (Event::class.isAssignableFrom(TicketCloseWithoutCommentEvent::class))
            internal.ticketCloseWithoutComment[uuid] = eventListener as TMEL<TicketCloseWithoutCommentEvent>
        if (Event::class.isAssignableFrom(TicketReadReceiptEvent::class))
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

fun <T: Any, U: Any> KClass<T>.isAssignableFrom(cls: KClass<U>) = this.java.isAssignableFrom(cls.java)

private inline fun safeExecute(f: () -> Unit) {
    try { f() }
    catch (e: Exception) { e.printStackTrace() }
}