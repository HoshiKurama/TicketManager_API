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
 * - The listener is also called for any supertype listeners. For example, TicketCloseEvent receives both
 * TicketCloseWithCommentEvent & TicketCloseWithoutCommentEvent events.
 * - New subscriptions can be created or destroyed on any thread and at any time.
 *
 * Note: TMEventBus differs from other registration points in that it is a concrete class. This is necessary because
 * to provide an awesome registration syntax, the bus inlines the subscribe code directly into your code. Consequently,
 * all internals are exposed by the bus. It's HIGHLY recommended that you not mess with anything inside the internal
 * property unless you have a very good reason for doing so.
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

        return internal.run {{
            listOf(ticketCreate, ticketAssign, ticketReopen, ticketComment, ticketMassClose,
                ticketSetPriority, ticketCloseWithComment, ticketCloseWithoutComment
            ).forEach { it.remove(uuid) }
        }}
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
            is TicketCreateEvent -> ticketCreate.values.forEach { subscriber ->
                TMCoroutine.Supervised.launch {
                    safeExecute { subscriber.onEvent(event) }
                }
            }
            is TicketCommentEvent -> ticketComment.values.forEach { subscriber ->
                TMCoroutine.Supervised.launch {
                    safeExecute { subscriber.onEvent(event) }
                }
            }
            is TicketAssignEvent -> ticketAssign.values.forEach { subscriber ->
                TMCoroutine.Supervised.launch {
                    safeExecute { subscriber.onEvent(event) }
                }
            }
            is TicketReopenEvent -> ticketReopen.values.forEach { subscriber ->
                TMCoroutine.Supervised.launch {
                    safeExecute { subscriber.onEvent(event) }
                }
            }
            is TicketCloseWithCommentEvent -> ticketCloseWithComment.values.forEach { subscriber ->
                TMCoroutine.Supervised.launch {
                    safeExecute { subscriber.onEvent(event) }
                }
            }
            is TicketCloseWithoutCommentEvent -> ticketCloseWithoutComment.values.forEach { subscriber ->
                TMCoroutine.Supervised.launch {
                    safeExecute { subscriber.onEvent(event) }
                }
            }
            is TicketSetPriorityEvent -> ticketSetPriority.values.forEach { subscriber ->
                TMCoroutine.Supervised.launch {
                    safeExecute { subscriber.onEvent(event) }
                }
            }
            is TicketMassCloseEvent -> ticketMassClose.values.forEach { subscriber ->
                TMCoroutine.Supervised.launch {
                    safeExecute { subscriber.onEvent(event) }
                }
            }
        }
    }
}

fun <T: Any, U: Any> KClass<T>.isAssignableFrom(cls: KClass<U>) = this.java.isAssignableFrom(cls.java)

private inline fun safeExecute(f: () -> Unit) {
    try { f() }
    catch (e: Exception) { e.printStackTrace() }
}