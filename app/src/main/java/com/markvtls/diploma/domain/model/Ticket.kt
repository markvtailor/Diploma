package com.markvtls.diploma.domain.model

import java.time.LocalDateTime


data class Ticket (
    val locationFrom: String,
    val locationFromSecondary: String,
    val locationTo: String,
    val locationToSecondary: String,
    val expireDate: LocalDateTime,
    val sum: Int
        )