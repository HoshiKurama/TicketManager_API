package com.github.hoshikurama.ticketmanager.api.java.event;

import com.github.hoshikurama.ticketmanager.api.events.TMEvent;

import java.util.function.Consumer;

public interface TMEventBusJava {
    Runnable subscribe(Consumer<TMEvent> listener);
}