package com.github.hoshikurama.ticketmanager.api.java.impl;

import com.github.hoshikurama.ticketmanager.api.events.TMEvent;
import com.github.hoshikurama.ticketmanager.api.java.events.TMEventBusJava;
import com.github.hoshikurama.ticketmanager.api.java.EventListenerAdapter;

import java.util.function.Consumer;

public class TMEventBusJavaImpl implements TMEventBusJava {

    @Override
    public Runnable subscribe(Consumer<TMEvent> listener) {
        var handle = EventListenerAdapter.INSTANCE.exposedSubscribe(listener);
        return handle::invoke;
    }
}
