package com.github.hoshikurama.ticketmanager.api.impl

import com.github.hoshikurama.ticketmanager.api.event.TMEventBus
import com.github.hoshikurama.ticketmanager.api.event.TMEventListener
import com.github.hoshikurama.ticketmanager.api.event.events.*
import com.github.hoshikurama.tmcoroutine.TMCoroutine

class TMEventBus : TMEventBus {

    // Future Me: Keep list of every unique event type
    private val ticketCreate = mutableListOf<TMEventListener<in TicketCreateEvent>>()
    private val ticketComment = mutableListOf<TMEventListener<in TicketCommentEvent>>()
    private val ticketAssign = mutableListOf<TMEventListener<in TicketAssignEvent>>()
    private val ticketReopen = mutableListOf<TMEventListener<in TicketReopenEvent>>()
    private val ticketCloseWithComment = mutableListOf<TMEventListener<in TicketCloseWithCommentEvent>>()
    private val ticketCloseWithoutComment = mutableListOf<TMEventListener<in TicketCloseWithoutCommentEvent>>()
    private val ticketSetPriority = mutableListOf<TMEventListener<in TicketSetPriorityEvent>>()
    private val ticketMassClose = mutableListOf<TMEventListener<in TicketMassCloseEvent>>()


    @Suppress("UNCHECKED_CAST") // Safe
    override fun <Event : TMEvent> subscribe(listener: TMEventListener<Event>) {

        // Future Me: Add this for every new unique object
        (listener as? TMEventListener<in TicketCreateEvent>)?.run(ticketCreate::add)
        (listener as? TMEventListener<in TicketCommentEvent>)?.run(ticketComment::add)
        (listener as? TMEventListener<in TicketAssignEvent>)?.run(ticketAssign::add)
        (listener as? TMEventListener<in TicketReopenEvent>)?.run(ticketReopen::add)
        (listener as? TMEventListener<in TicketCloseWithCommentEvent>)?.run(ticketCloseWithComment::add)
        (listener as? TMEventListener<in TicketCloseWithoutCommentEvent>)?.run(ticketCloseWithoutComment::add)
        (listener as? TMEventListener<in TicketSetPriorityEvent>)?.run(ticketSetPriority::add)
        (listener as? TMEventListener<in TicketMassCloseEvent>)?.run(ticketMassClose::add)

        /* TODO REMOVE IF EVENT SYSTEM WORKS
        if (TicketCreateEvent::class.isAssignableFrom(scope))
            ticketCreate.add(listener as TMEventListener<TicketCreateEvent>)
         */
    }

    suspend fun callAsync(event: TMEvent): Unit = when (event) {
        is TicketCreateEvent -> ticketCreate.forEach { subscriber ->
            TMCoroutine.Supervised.launch {
                safeExecute { subscriber.onEvent(event) }
            }
        }
        is TicketCommentEvent -> ticketComment.forEach { subscriber ->
            TMCoroutine.Supervised.launch {
                safeExecute { subscriber.onEvent(event) }
            }
        }
        is TicketAssignEvent -> ticketAssign.forEach { subscriber ->
            TMCoroutine.Supervised.launch {
                safeExecute { subscriber.onEvent(event) }
            }
        }
        is TicketReopenEvent -> ticketReopen.forEach { subscriber ->
            TMCoroutine.Supervised.launch {
                safeExecute { subscriber.onEvent(event) }
            }
        }
        is TicketCloseWithCommentEvent -> ticketCloseWithComment.forEach { subscriber ->
            TMCoroutine.Supervised.launch {
                safeExecute { subscriber.onEvent(event) }
            }
        }
        is TicketCloseWithoutCommentEvent -> ticketCloseWithoutComment.forEach { subscriber ->
            TMCoroutine.Supervised.launch {
                safeExecute { subscriber.onEvent(event) }
            }
        }
        is TicketSetPriorityEvent -> ticketSetPriority.forEach { subscriber ->
            TMCoroutine.Supervised.launch {
                safeExecute { subscriber.onEvent(event) }
            }
        }
        is TicketMassCloseEvent -> ticketMassClose.forEach { subscriber ->
            TMCoroutine.Supervised.launch {
                safeExecute { subscriber.onEvent(event) }
            }
        }
    }
}

/* TODO REMOVE IF EVENT SYSTEM WORKS
private fun <T : TMEvent> KClass<out TMEvent>.isAssignableFrom(cls: KClass<T>): Boolean {
    return this::class.java.isAssignableFrom(cls.java)
}
 */

private inline fun safeExecute(f: () -> Unit) {
    try { f() }
    catch (e: Exception) { e.printStackTrace() }
}