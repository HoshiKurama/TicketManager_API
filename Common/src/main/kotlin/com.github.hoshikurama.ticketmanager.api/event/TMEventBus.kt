package com.github.hoshikurama.ticketmanager.api.event

import com.github.hoshikurama.ticketmanager.api.event.events.TMEvent

interface TMEventBus {
    fun <Event : TMEvent> subscribe(listener: TMEventListener<Event>)
}