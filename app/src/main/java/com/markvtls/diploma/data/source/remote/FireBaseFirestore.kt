package com.markvtls.diploma.data.source.remote

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.markvtls.diploma.MainActivity
import com.markvtls.diploma.data.pojo.TicketPojo
import com.markvtls.diploma.domain.model.Ticket
import com.markvtls.diploma.domain.model.toDomain
import java.time.format.DateTimeFormatter
import java.util.*


class FireBaseFirestore {

    private var db = FirebaseFirestore.getInstance()





    fun saveTicketToDataBase(ticket: Ticket) {

        val user = Firebase.auth.currentUser

        val userIdentificator =
            if (user?.email != null) {
                user.email
            } else user?.phoneNumber

        val formatter =  DateTimeFormatter.ofPattern("dd.MM.yyyy")

        val ticketData = hashMapOf(
            "locationTo" to ticket.locationTo,
            "locationToSecondary" to ticket.locationToSecondary,
            "locationFrom" to ticket.locationFrom,
            "locationFromSecondary" to ticket.locationFromSecondary,
            "sum" to ticket.sum,
            "expireDate" to ticket.expireDate.format(formatter).toString()
        )

        println("USR ${Firebase.auth}")
        println("SUCCESS $userIdentificator")
        if (userIdentificator != null) {
            db.collection(userIdentificator)
                .document("Ticket ${UUID.randomUUID()}")
                .set(ticketData)
        }
    }

    fun getUserTickets(listener: MutableLiveData<List<Ticket>>) {

        val user = Firebase.auth.currentUser

        val userIdentificator =
            if (user?.email != null) {
                user.email
            } else user?.phoneNumber

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