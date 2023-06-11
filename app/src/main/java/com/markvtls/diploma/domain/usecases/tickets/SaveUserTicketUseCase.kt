package com.markvtls.diploma.domain.usecases.tickets

import com.markvtls.diploma.domain.model.Ticket
import com.markvtls.diploma.domain.repository.TicketRepository
import javax.inject.Inject

class SaveUserTicketUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    suspend operator fun invoke(ticket: Ticket) {
        repository.saveUserTicket(ticket)
    }
}