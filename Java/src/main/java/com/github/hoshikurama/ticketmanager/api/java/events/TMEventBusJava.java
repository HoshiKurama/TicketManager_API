package com.github.hoshikurama.ticketmanager.api.java.events;

import com.github.hoshikurama.ticketmanager.api.events.TMEvent;

import java.util.function.Consumer;

/**
 * This event bus distributes TicketManager events of supertype TMEvent to subscribed listeners registered via the
 * #subscribe() function. These listeners begin execution asynchronously in an off-thread coroutine, which is not
 * accessible to Java users. Registration and deregistration are thread-safe.
 * <p>
 * The true Common/Kotlin Event Bus utilizes a reified inline function, which is impossible to call outside of Kotlin.
 * Thus, this entry point is required for Java users wishing to register an event listener.
 */
public interface TMEventBusJava {
    /**
     * Registers a listener that will execute when type Event is either: (1) a supertype of, or (2) the same type as the
     * fired event. See TMEvent above for more information about the event bus.
     * @param eventClass Event type to listen for. These are contained in the events package of the Common module.
     * @param listener registered listener.
     * @return function that unregisters the listener when invoked.
     */
    @SuppressWarnings("unused")
    <Event extends TMEvent> Runnable subscribe(Class<Event> eventClass, Consumer<Event> listener);
}