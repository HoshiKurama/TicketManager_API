package com.github.hoshikurama.ticketmanager.api.event

import com.github.hoshikurama.ticketmanager.api.event.events.TMEvent

fun interface TMEventListener<Event : TMEvent> {
    suspend fun onEvent(event: Event)
}