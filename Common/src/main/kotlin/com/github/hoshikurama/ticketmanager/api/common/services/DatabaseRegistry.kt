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
     * @return the closeDatabase function
     */
    fun register1(
        databaseName: String,
        builder: () -> AsyncDatabase
    ): () -> Unit

    /**
     * Registers a database builder. This function is for Kotlin users not wishing to use coroutines!
     * @param databaseName exact name users must type into the TicketManager SE config file
     * @param builder function to build the database
     * @return the closeDatabase function
     */
    fun register2(
        databaseName: String,
        builder: () -> CompletableFutureAsyncDatabase
    ): () -> Unit {
        val converter = { AsyncDBAdapter(builder()) }
        return register1(databaseName, converter)
    }

    /**
     * Registers a database builder. This function is for non-Kotlin users!
     * @param databaseName exact name users must type into the TicketManager SE config file
     * @param builder function to build the database
     */
    fun register3(
        databaseName: String,
        builder: Supplier<CompletableFutureAsyncDatabase>
    ): Supplier<Void?> {
        val converter = { AsyncDBAdapter(builder.get()) }
        val f = register1(databaseName, converter)
        return Supplier { f(); null }
    }
}