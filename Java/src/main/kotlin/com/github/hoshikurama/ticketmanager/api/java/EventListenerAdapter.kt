package com.github.hoshikurama.ticketmanager.api.java

import com.github.hoshikurama.ticketmanager.api.event.TMEventListener
import com.github.hoshikurama.ticketmanager.api.event.events.TMEvent
import com.github.hoshikurama.ticketmanager.api.java.event.TMEventListenerJava

class EventListenerAdapter<Event : TMEvent>(private val javaVersion: TMEventListenerJava<Event>) : TMEventListener<Event> {
    override suspend fun onEvent(event: Event) {
        javaVersion.onEvent(event)
    }
}