package com.markvtls.diploma.domain.usecases.tickets

import com.markvtls.diploma.domain.model.Ticket
import com.markvtls.diploma.domain.repository.TicketRepository
import javax.inject.Inject

class GetUserTicketsUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    suspend operator fun invoke(userIdentificator: String): List<Ticket> {
        return repository.getUserTickets(userIdentificator)
    }
}