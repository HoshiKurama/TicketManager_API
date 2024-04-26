package com.github.hoshikurama.ticketmanager.api.java

import com.github.hoshikurama.ticketmanager.api.events.TMEvent
import com.github.hoshikurama.ticketmanager.api.impl.TMEventBus
import java.util.function.Consumer

object EventListenerAdapter {
    fun <Event: TMEvent> create(listener: Consumer<Event>): TMEventBus.Internal.EventListener<Event> {
        return TMEventBus.Internal.EventListener(listener::accept)
    }
}