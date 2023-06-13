package com.github.hoshikurama.ticketmanager.api.paper

import com.github.hoshikurama.ticketmanager.api.common.services.DatabaseRegistry

@Suppress("Unused")
/**
 * Paper-specific endpoint for external plugins to register a database type.
 * @see DatabaseRegistry
 */
class TicketManagerDatabaseRegister(private val backingSource: DatabaseRegistry): DatabaseRegistry by backingSource