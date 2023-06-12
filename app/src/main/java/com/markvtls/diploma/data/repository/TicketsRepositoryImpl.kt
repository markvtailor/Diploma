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
        /*return listOf(
            Ticket("Якутск", "Причал 1", "Владивосток", "Причал 2", LocalDateTime.now(), 3999),
            Ticket("Нижний Новгород", "Причал 1", "Пенза", "Причал 2", LocalDateTime.now(), 4999),
            Ticket("Екатеринбург", "Причал 4", "Челябинск", "Причал 5", LocalDateTime.now(), 5999),
            Ticket("Сысерть", "Причал 1", "Сызрань", "Причал 5", LocalDateTime.now(), 6999),
            Ticket("Омск", "Причал 6", "Подольск", "Причал 8", LocalDateTime.now().plusDays(7), 7999),
            Ticket("Воронеж", "Причал 1", "Хабаровск", "Причал 4", LocalDateTime.now().plusDays(7), 8999),
            Ticket("Саратов", "Причал 9", "Саранск", "Причал 6", LocalDateTime.now().plusDays(7), 9999),
            Ticket("Москва", "Причал 3", "Петушки", "Причал 6", LocalDateTime.now().plusDays(7), 10999)
            )*/
    }
}