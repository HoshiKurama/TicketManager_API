package com.github.hoshikurama.ticketmanager.api.common.events

/**
 * Platform-agnostic interface used to call events from within TicketManager
 */
interface TMEvent {
    fun callEventTM()
}