package com.github.hoshikurama.ticketmanager.api.common.services

import com.github.hoshikurama.ticketmanager.api.common.database.AsyncDBAdapter
import com.github.hoshikurama.ticketmanager.api.common.database.CompletableFutureAsyncDatabase
import com.github.hoshikurama.ticketmanager.api.common.database.AsyncDatabase
import java.util.function.Supplier

/**
 * Represents the database registry for users wishing to add or remove a database extension.
 *
 * Note: TicketManager does not reload database extensions. However, TicketManager does make an internal call to load
 * the latest AsyncDatabase builder function associated with the user's requested database type. Thus, it is
 * advisable to submit a builder function which also contains instructions for acquiring the config values.
 */
@Suppress("Unused")
interface DatabaseRegistry {

    /**
     * Registers a database builder. This function is only for Kotlin users wishing to use coroutines!
     * @param databaseName exact name users must type into the TicketManager SE config file
     * @param builder function to build the database
     */
    fun register(databaseName: String, builder: () -> AsyncDatabase)

    /**
     * Registers a database builder. This function is for Kotlin users not wishing to use coroutines!
     * @param databaseName exact name users must type into the TicketManager SE config file
     * @param builder function to build the database
     */
    fun register(databaseName: String, builder: () -> CompletableFutureAsyncDatabase) {
        val converter = { AsyncDBAdapter(builder()) }
        register(databaseName, converter)
    }

    /**
     * Registers a database builder. This function is for non-Kotlin users!
     * @param databaseName exact name users must type into the TicketManager SE config file
     * @param builder function to build the database
     */
    fun register(databaseName: String, builder: Supplier<CompletableFutureAsyncDatabase>) {
        val converter = { AsyncDBAdapter(builder.get()) }
        register(databaseName, converter)
    }
}