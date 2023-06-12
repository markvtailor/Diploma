package com.markvtls.diploma.data.repository

import androidx.lifecycle.MutableLiveData
import com.markvtls.diploma.data.source.remote.FireBaseFirestore
import com.markvtls.diploma.domain.model.Ticket
import com.markvtls.diploma.domain.repository.TicketRepository
import javax.inject.Inject

class TicketsRepositoryImpl @Inject constructor(
    private val firestore: FireBaseFirestore
): TicketRepository {
    override suspend fun saveUserTicket(ticket: Ticket) {
        firestore.saveTicketToDataBase(ticket)
    }

    override suspend fun getUserTickets(userIdentificator: String, listener: MutableLiveData<List<Ticket>>){
        firestore.getUserTickets(listener)
    }
}