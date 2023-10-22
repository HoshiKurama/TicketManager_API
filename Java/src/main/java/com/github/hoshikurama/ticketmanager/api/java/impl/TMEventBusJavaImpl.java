package com.github.hoshikurama.ticketmanager.api.java.impl;

import com.github.hoshikurama.ticketmanager.api.event.events.TMEvent;
import com.github.hoshikurama.ticketmanager.api.impl.TicketManager;
import com.github.hoshikurama.ticketmanager.api.java.event.TMEventBusJava;
import com.github.hoshikurama.ticketmanager.api.java.event.TMEventListenerJava;
import com.github.hoshikurama.ticketmanager.api.java.EventListenerAdapter;

public class TMEventBusJavaImpl implements TMEventBusJava {

    @Override
    public <Event extends TMEvent> void subscribe(TMEventListenerJava<Event> listener) {
        final var listenerKotlin = new EventListenerAdapter<>(listener);
        TicketManager.INSTANCE.getEventBus().subscribe(listenerKotlin);
    }
}
