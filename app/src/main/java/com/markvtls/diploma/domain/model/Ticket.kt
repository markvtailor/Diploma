package com.markvtls.diploma.domain.model

import com.markvtls.diploma.data.pojo.TicketPojo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


data class Ticket (
    val locationFrom: String,
    val locationFromSecondary: String,
    val locationTo: String,
    val locationToSecondary: String,
    val expireDate: LocalDate,
    val sum: Int
        )


fun TicketPojo.toDomain(): Ticket {
    val formatter =  DateTimeFormatter.ofPattern("dd.MM.yyyy")

    return Ticket(
        locationFrom = locationFrom,
        locationFromSecondary = locationFromSecondary,
        locationTo = locationTo,
        locationToSecondary = locationToSecondary,
        sum = sum,
        expireDate = LocalDate.parse(expireDate,formatter)
    )
}