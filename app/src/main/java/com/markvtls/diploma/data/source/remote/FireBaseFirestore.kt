package com.markvtls.diploma.data.source.remote

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.markvtls.diploma.data.pojo.TicketPojo
import com.markvtls.diploma.domain.model.Ticket
import com.markvtls.diploma.domain.model.toDomain
import java.time.format.DateTimeFormatter
import java.util.*


class FireBaseFirestore {

    private var db = FirebaseFirestore.getInstance()


    private val userIdentificator =
        if (FirebaseAuth.getInstance().currentUser?.email != null) {
            FirebaseAuth.getInstance().currentUser?.email
        } else FirebaseAuth.getInstance().currentUser?.phoneNumber


    fun saveTicketToDataBase(ticket: Ticket) {

        val formatter =  DateTimeFormatter.ofPattern("dd.MM.yyyy")

        val ticketData = hashMapOf(
            "locationTo" to ticket.locationTo,
            "locationToSecondary" to ticket.locationToSecondary,
            "locationFrom" to ticket.locationFrom,
            "locationFromSecondary" to ticket.locationFromSecondary,
            "sum" to ticket.sum,
            "expireDate" to ticket.expireDate.format(formatter).toString()
        )

        if (userIdentificator != null) {
            db.collection(userIdentificator)
                .document("Ticket ${UUID.randomUUID()}")
                .set(ticketData)
        }
    }

    fun getUserTickets(listener: MutableLiveData<List<Ticket>>) {
        if (userIdentificator != null) {
            db.collection(userIdentificator)
                .get()
                .addOnSuccessListener { documents->
                    val userTickets = mutableListOf<Ticket>()
                    for (document in documents) {
                        val ticket = document.toObject(TicketPojo::class.java)
                        userTickets.add(ticket.toDomain())
                    }

                    listener.value = userTickets




                }
        }
    }
}