package com.github.hoshikurama.ticketmanager.api.java

import com.github.hoshikurama.ticketmanager.api.registry.database.AsyncDatabase
import com.github.hoshikurama.ticketmanager.api.java.registry.database.AsyncDatabaseJava
import com.github.hoshikurama.ticketmanager.api.registry.database.utils.DBResult
import com.github.hoshikurama.ticketmanager.api.registry.database.utils.SearchConstraints
import com.github.hoshikurama.ticketmanager.api.ticket.*
import kotlinx.coroutines.future.asDeferred

class AsyncDatabaseAdapter(private val db: AsyncDatabaseJava) : AsyncDatabase {

    override suspend fun setAssignmentAsync(ticketID: Long, assignment: Assignment) {
        return db.setAssignmentAsync(ticketID, assignment)
    }

    override suspend fun setCreatorStatusUpdateAsync(ticketID: Long, status: Boolean) {
        return db.setCreatorStatusUpdateAsync(ticketID, status)
    }

    override suspend fun setPriorityAsync(ticketID: Long, priority: Ticket.Priority) {
        return db.setPriorityAsync(ticketID, priority)
    }

    override suspend fun setStatusAsync(ticketID: Long, status: Ticket.Status) {
        return db.setStatusAsync(ticketID, status)
    }

    override suspend fun insertActionAsync(id: Long, action: Action) {
        return db.insertActionAsync(id, action)
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
    ) {
        return db.massCloseTicketsAsync(lowerBound, upperBound, actor, ticketLoc)
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