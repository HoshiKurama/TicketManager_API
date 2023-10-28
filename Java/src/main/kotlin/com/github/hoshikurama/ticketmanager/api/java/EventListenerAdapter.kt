package com.github.hoshikurama.ticketmanager.api.java

import com.github.hoshikurama.ticketmanager.api.events.TMEvent
import com.github.hoshikurama.ticketmanager.api.impl.TicketManager
import java.util.function.Consumer

object EventListenerAdapter {
    fun exposedSubscribe(javaListener: Consumer<TMEvent>): () -> Unit {
        return TicketManager.EventBus.subscribe<TMEvent>(javaListener::accept)
    }
}