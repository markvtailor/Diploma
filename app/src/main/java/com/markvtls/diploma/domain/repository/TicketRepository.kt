package com.markvtls.diploma.domain.repository

import com.markvtls.diploma.domain.model.Ticket

interface TicketRepository {

    suspend fun saveUserTicket(ticket: Ticket)

    suspend fun getUserTickets(userIdentificator: String): List<Ticket>
}