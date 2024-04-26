package com.github.hoshikurama.ticketmanager.api.java.events;

import com.github.hoshikurama.ticketmanager.api.events.TMEvent;

import java.util.function.Consumer;
// TODO UPDATE LANGUAGE USED FOR NEW UPDATE
/**
 * The TMEventBus distributes all TicketManager events to any subscribed listeners in a thread-safe manner. Please note:
 * Java users subscribe to ALL events and then filter/typecast as needed.
 * <p>
 * The true Common/Kotlin Event Bus utilizes reified inline functions, something that is impossible to call outside of Kotlin.
 * Consequently, the event bus is significantly dumbed down and exposed in such a way that it is accessible to Java users.
 * <p>
 * Additionally, new subscriptions can be created or destroyed on any thread and at any time.
 */
public interface TMEventBusJava {
    /**
     * Allows users to subscribe to TicketManager events. This can be called at any time and on any thread.
     * @return a function to unregister your listener
     */
    @SuppressWarnings("unused")
    <Event extends TMEvent> Runnable subscribe(Class<Event> eventClass, Consumer<Event> listener);
}