package com.github.hoshikurama.ticketmanager.api.java.event;

import com.github.hoshikurama.ticketmanager.api.event.events.TMEvent;

public interface TMEventBusJava {
    <Event extends TMEvent> void subscribe(TMEventListenerJava<Event> listener);
}