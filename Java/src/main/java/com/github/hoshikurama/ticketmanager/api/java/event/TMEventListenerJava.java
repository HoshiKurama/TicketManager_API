package com.github.hoshikurama.ticketmanager.api.java.event;

import com.github.hoshikurama.ticketmanager.api.event.events.TMEvent;
import org.jetbrains.annotations.NotNull;

public interface TMEventListenerJava<Event extends TMEvent> {
    void onEvent(@NotNull Event event);
}
