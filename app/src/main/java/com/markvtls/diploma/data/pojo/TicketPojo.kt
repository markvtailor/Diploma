package com.markvtls.diploma.data.pojo

data class TicketPojo(
    val locationFrom: String = "",
    val locationFromSecondary: String = "",
    val locationTo: String = "",
    val locationToSecondary: String = "",
    val expireDate: String = "",
    val sum: Int = 0)
