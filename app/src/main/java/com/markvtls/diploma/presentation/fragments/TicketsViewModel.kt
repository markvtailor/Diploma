package com.markvtls.diploma.presentation.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markvtls.diploma.domain.model.Ticket
import com.markvtls.diploma.domain.usecases.tickets.GetUserTicketsUseCase
import com.markvtls.diploma.domain.usecases.tickets.SaveUserTicketUseCase
import com.markvtls.diploma.presentation.PaymentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketsViewModel @Inject constructor(
    private val getUserTicketsUseCase: GetUserTicketsUseCase,
    private val saveUserTicketUseCase: SaveUserTicketUseCase
): ViewModel() {

    sealed class Event {
        object FeedEnd : Event()
        object ConnectionError : Event()
        object ServerError : Event()

    }

    var lastTicketStartPoint: String = ""
    var lastTicketDestination: String = ""
    var lastTicketType: String = ""
    var lastTicketAdultsCount = 0
    var lastTicketKidsCount = 0
    var lastTicketCar = true
    var lastTicketHeavyCar = false
    var lastTicketCargoCar = false
    var lastTicketSum = 0

    val lastTicketPaymentType: MutableLiveData<PaymentType> by lazy {
        MutableLiveData<PaymentType>(PaymentType.MASTERCARD)
    }

    val newTickets = mutableListOf<Ticket>()

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private val _currentTickets: MutableLiveData<List<Ticket>> by lazy {
        MutableLiveData<List<Ticket>>()
    }

    val currentTickets: LiveData<List<Ticket>> get() = _currentTickets


    fun loadUserTickets(userIdentificator: String?) {
        if (userIdentificator != null) {
            viewModelScope.launch(Dispatchers.IO + SupervisorJob()) {
                try {
                    val tickets = getUserTicketsUseCase(userIdentificator)
                    _currentTickets.postValue(tickets)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun saveUserTicket(ticket: Ticket) {
        viewModelScope.launch(Dispatchers.IO + SupervisorJob()) {
            try {
                saveUserTicketUseCase(ticket)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}