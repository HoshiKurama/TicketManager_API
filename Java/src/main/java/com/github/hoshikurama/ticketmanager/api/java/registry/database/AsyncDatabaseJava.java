package com.github.hoshikurama.ticketmanager.api.java.registry.database;

import com.github.hoshikurama.ticketmanager.api.registry.database.utils.DBResult;
import com.github.hoshikurama.ticketmanager.api.registry.database.utils.SearchConstraints;
import com.github.hoshikurama.ticketmanager.api.ticket.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@SuppressWarnings("unused")
public interface AsyncDatabaseJava {

    // Individual property setters

    /**
     * Asynchronously set the assignment of a Ticket. This should have no other side effects.
     * @param ticketID ID of ticket to modify
     * @param assignment Assignment value. Null indicates an assignment of nobody
     */
    void setAssignmentAsync(@NotNull Long ticketID, @NotNull Assignment assignment) throws @NotNull Exception;

    /**
     * Asynchronously set the value indicating if the creator has seen the most recent action.
     * This should have no other side effects.
     * @param ticketID ID of ticket to modify
     * @param status true indicates the user has an update to read. False indicates otherwise.
     */
    void setCreatorStatusUpdateAsync(@NotNull Long ticketID, @NotNull Boolean status) throws @NotNull Exception;

    /**
     * Asynchronously change the ticket priority. This should have no other side effects.
     * @param ticketID ID of ticket to modify
     * @param priority Priority of ticket
     */
    void setPriorityAsync(@NotNull Long ticketID, @NotNull Ticket.Priority priority) throws @NotNull Exception;

    /**
     * Asynchronously set the OPEN or CLOSED status of a ticket. This should have no other side effects.
     * @param ticketID ID of ticket to modify
     * @param status Ticket Status (Open/Closed)
     */
    void setStatusAsync(@NotNull Long ticketID, @NotNull Ticket.Status status) throws @NotNull Exception;

    // Database Additions

    /**
     * Asynchronously append an Action to a ticket. This should have no other side effects.
     * @param id ID of ticket to modify
     * @param action Action to append
     */
    void insertActionAsync(@NotNull Long id, @NotNull Action action) throws @NotNull Exception;

    /**
     * Asynchronously add an initial ticket to the plugin. Tickets inserted with this function do not have a proper
     * id yet, which must be created here. Extensions are responsible for preventing ID collisions.
     * @param ticket Ticket to append
     * @return Uniquely assigned ticket ID
     */
    @NotNull CompletableFuture<@NotNull Long> insertNewTicketAsync(@NotNull Ticket ticket) throws @NotNull Exception;

    // Get Ticket

    /**
     * Asynchronously retrieve a ticket if it exists.
     * @param id Desired ticket ID
     * @return Ticket if the id is found and null otherwise
     */
    @NotNull CompletableFuture<@Nullable Ticket> getTicketOrNullAsync(@NotNull Long id) throws @NotNull Exception;

    // Aggregate Operations

    /**
     * Asynchronously retrieve a paginated list of tickets with an open status. This is returned in the form of a Result object.
     * If the page size is 0, then the results should not be paginated.
     * @param page Requested page of pagination
     * @param pageSize Number of entries per page
     * @return See Result for specific returned information.
     * @see DBResult
     */
    @NotNull CompletableFuture<@NotNull DBResult> getOpenTicketsAsync(@NotNull Integer page, @NotNull Integer pageSize) throws @NotNull Exception;

    /**
     * Asynchronously retrieve a paginated list of tickets which have an open status and a specific assignment.
     * This is returned in the form of a Result object. Users wishing to search for tickets assigned to nobody should
     * use the next function instead.
     * If the page size is 0, then the results will not be paginated.
     * @param page Requested page of pagination
     * @param pageSize Number of entries per page
     * @param assignments List of assignments to check
     * @return See Result for specific returned information.
     * @see DBResult
     */
    @NotNull CompletableFuture<@NotNull DBResult> getOpenTicketsAssignedToAsync(
            @NotNull Integer page,
            @NotNull Integer pageSize,
            @NotNull List<@NotNull Assignment> assignments
    ) throws @NotNull Exception;

    /**
     * Asynchronously retrieve a paginated list of tickets which have an open status and assigned to nobody.
     * This is returned in the form of a Result object.
     * If the page size is 0, then the results will not be paginated.
     * @param page Requested page of pagination
     * @param pageSize Number of entries per page
     * @return See Result for specific returned information.
     * @see DBResult
     */
    @NotNull CompletableFuture<@NotNull DBResult> getOpenTicketsNotAssignedAsync(
            @NotNull Integer page,
            @NotNull Integer pageSize
    ) throws @NotNull Exception;

    /**
     * Asynchronously close all tickets between a lower and upper bound inclusive. Extensions have the following obligation
     * for each ticket:
     * - Set status to close
     * - insert a Mass-close action. The actor and ticket location are provided to create this entry.
     * @param lowerBound Lower bound inclusive
     * @param upperBound Upper bound inclusive
     * @param actor Creator who initiated the ticket modification
     * @param ticketLoc Location where Creator made the ticket modification
     */
    void massCloseTicketsAsync(
            @NotNull Long lowerBound,
            @NotNull Long upperBound,
            @NotNull Creator actor,
            @NotNull ActionLocation ticketLoc
    ) throws @NotNull Exception;

    // Counting

    /**
     * Asynchronously acquire the number of currently open tickets.
     */
    @NotNull CompletableFuture<@NotNull Long> countOpenTicketsAsync() throws @NotNull Exception;

    /**
     * Asynchronously acquire the number of open tickets assigned to a particular user or to a set of permission groups
     * @param assignments List of assignments to check
     */
    @NotNull CompletableFuture<@NotNull Long> countOpenTicketsAssignedToAsync(
            @NotNull List<@NotNull Assignment> assignments
    ) throws @NotNull Exception;

    // Searching

    /**
     * Asynchronously search through the entire database for all tickets which meet particular search constraints.
     * @param constraints See SearchConstraints for more information
     * @param pageSize Size of each page of tickets
     * @return See Result for more information
     * @see SearchConstraints
     * @see DBResult
     */
    @NotNull CompletableFuture<@NotNull DBResult> searchDatabaseAsync(
            @NotNull SearchConstraints constraints,
            @NotNull Integer pageSize
    ) throws @NotNull Exception;

    // ID Acquisition

    /**
     * Asynchronously retrieve all ticket IDs for any ticket that the creator has not viewed the most recent update.
     * @return list of ticket IDs
     */
    @NotNull CompletableFuture<@NotNull List<@NotNull Long>> getTicketIDsWithUpdatesAsync() throws @NotNull Exception;

    /**
     * Asynchronously retrieve all ticket IDs where a particular user has not viewed the most recent update.
     * @param creator creator of the tickets
     * @return list of ticket IDs
     */
    @NotNull CompletableFuture<@NotNull List<@NotNull Long>> getTicketIDsWithUpdatesForAsync(@NotNull Creator creator) throws @NotNull Exception;

    /**
     * Asynchronously retrieve all ticket IDs of tickets owned by a particular Creator
     * @param creator Ticket creator
     * @return list of ticket IDs
     */
    @NotNull CompletableFuture<@NotNull List<@NotNull Long>> getOwnedTicketIDsAsync(@NotNull Creator creator) throws @NotNull Exception;

    /**
     * Asynchronously retrieve all ticket IDs of tickets currently open
     * @return list of ticket IDs
     */
    @NotNull CompletableFuture<@NotNull List<@NotNull Long>> getOpenTicketIDsAsync() throws @NotNull Exception;

    /**
     * Asynchronously retrieve all ticket IDs of tickets currently open and belonging to a particular creator.
     */
    @NotNull CompletableFuture<@NotNull List<@NotNull Long>> getOpenTicketIDsForUser(@NotNull Creator creator) throws @NotNull Exception;

    // Internal Database Functions

    /**
     * Shuts down the database. This function runs on the Common ForkJoinPool as a blocking call during server shutdown
     * and plugin reloading. Do not let this function complete until the database is fully shut down.
     */
    void closeDatabase() throws @NotNull Exception;

    /**
     * Initializes or re-initializes the database. This function runs on the Common ForkJoinPool as a blocking call
     * during server shutdown and plugin reloading. Do not let this function complete until the database is ready for use.
     */
    void initializeDatabase() throws @NotNull Exception;
}