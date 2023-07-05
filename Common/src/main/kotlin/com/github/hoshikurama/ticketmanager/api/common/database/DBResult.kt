package com.github.hoshikurama.ticketmanager.api.common.database

import com.github.hoshikurama.ticketmanager.api.common.ticket.Ticket
import kotlinx.collections.immutable.ImmutableList

/**
 * For various reasons, certain AsyncDatabase function return a DBResult object containing various results.
 * In general, this is returned when the plugin expects numerous results to be returned, more than can be displayed
 * at once on a Minecraft instance's screen. Thus, the results are often chunked into pages and only the requested page is returned.
 * @property filteredResults Final list of tickets applicable to the called function
 * @property totalPages Number of pages found
 * @property totalResults Number of results found across all pages
 * @property returnedPage True page number returned
 */
@JvmRecord
data class DBResult(
    val filteredResults: ImmutableList<Ticket>,
    val totalPages: Int,
    val totalResults: Int,
    val returnedPage: Int
)