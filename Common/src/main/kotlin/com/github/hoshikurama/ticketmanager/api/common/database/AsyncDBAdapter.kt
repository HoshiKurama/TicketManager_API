package com.github.hoshikurama.ticketmanager.api.common.database

import com.github.hoshikurama.ticketmanager.api.common.ticket.*
import com.github.hoshikurama.ticketmanager.api.common.ticket.Ticket
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.future.asDeferred
import java.util.concurrent.CompletableFuture

class AsyncDBAdapter(private val db: CompletableFutureAsyncDatabase) : AsyncDatabase {

    private suspend fun CompletableFuture<Void>.asDeferredUnit(): Deferred<Unit> {
        this.asDeferred().await()
        return CompletableDeferred(Unit)
    }

    override suspend fun setAssignmentAsync(ticketID: Long, assignment: Assignment): Deferred<Unit> {
        return db.setAssignmentAsync(ticketID, assignment).asDeferredUnit()
    }

    override suspend fun setCreatorStatusUpdateAsync(ticketID: Long, status: Boolean): Deferred<Unit> {
        return db.setCreatorStatusUpdateAsync(ticketID, status).asDeferredUnit()
    }

    override suspend fun setPriorityAsync(ticketID: Long, priority: Ticket.Priority): Deferred<Unit> {
        return db.setPriorityAsync(ticketID, priority).asDeferredUnit()
    }

    override suspend fun setStatusAsync(ticketID: Long, status: Ticket.Status): Deferred<Unit> {
        return db.setStatusAsync(ticketID, status).asDeferredUnit()
    }

    override suspend fun insertActionAsync(id: Long, action: Action): Deferred<Unit> {
        return db.insertActionAsync(id, action).asDeferredUnit()
    }

    override suspend fun insertNewTicketAsync(ticket: Ticket): Long {
        return db.insertNewTicketAsync(ticket).asDeferred().await()
    }

    override suspend fun getTicketOrNullAsync(id: Long): Ticket? {
        return db.getTicketOrNullAsync(id).asDeferred().await()
    }

    override suspend fun getOpenTicketsAsync(page: Int, pageSize: Int): DBResult {
        return db.getOpenTicketsAsync(page, pageSize).asDeferred().await()
    }

    override suspend fun getOpenTicketsAssignedToAsync(
        page: Int,
        pageSize: Int,
        assignments: List<Assignment>
    ): DBResult {
        return db.getOpenTicketsAssignedToAsync(page, pageSize, assignments).asDeferred().await()
    }

    override suspend fun getOpenTicketsNotAssignedAsync(page: Int, pageSize: Int): DBResult {
        return db.getOpenTicketsNotAssignedAsync(page, pageSize).asDeferred().await()
    }

    override suspend fun massCloseTicketsAsync(
        lowerBound: Long,
        upperBound: Long,
        actor: Creator,
        ticketLoc: ActionLocation
    ): Deferred<Unit> {
        return db.massCloseTicketsAsync(lowerBound, upperBound, actor, ticketLoc).asDeferredUnit()
    }

    override suspend fun countOpenTicketsAsync(): Long {
        return db.countOpenTicketsAsync().asDeferred().await()
    }

    override suspend fun countOpenTicketsAssignedToAsync(assignments: List<Assignment>): Long {
        return db.countOpenTicketsAssignedToAsync(assignments).asDeferred().await()
    }

    override suspend fun searchDatabaseAsync(constraints: SearchConstraints, pageSize: Int): DBResult {
        return db.searchDatabaseAsync(constraints, pageSize).asDeferred().await()
    }

    override suspend fun getTicketIDsWithUpdatesAsync(): List<Long> {
        return db.getTicketIDsWithUpdatesAsync().asDeferred().await()
    }

    override suspend fun getTicketIDsWithUpdatesForAsync(creator: Creator): List<Long> {
        return db.getTicketIDsWithUpdatesForAsync(creator).asDeferred().await()
    }

    override suspend fun getOwnedTicketIDsAsync(creator: Creator): List<Long> {
        return db.getOwnedTicketIDsAsync(creator).asDeferred().await()
    }

    override suspend fun getOpenTicketIDsAsync(): List<Long> {
        return db.getOpenTicketIDsAsync().asDeferred().await()
    }

    override suspend fun getOpenTicketIDsForUser(creator: Creator): List<Long> {
        return db.getOpenTicketIDsForUser(creator).asDeferred().await()
    }

    override fun closeDatabase() {
        db.closeDatabase()
    }

    override fun initializeDatabase() {
        db.initializeDatabase()
    }
}