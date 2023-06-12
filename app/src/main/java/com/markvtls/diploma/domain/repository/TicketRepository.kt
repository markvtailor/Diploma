package com.markvtls.diploma.domain.repository

import androidx.lifecycle.MutableLiveData
import com.markvtls.diploma.domain.model.Ticket

interface TicketRepository {

    suspend fun saveUserTicket(ticket: Ticket)

    suspend fun getUserTickets(userIdentificator: String, listener: MutableLiveData<List<Ticket>>)
}