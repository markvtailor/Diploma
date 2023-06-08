package com.markvtls.diploma.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveUserPhone(userPhone: String)

    suspend fun saveUserEmail(userEmail: String)

    fun getUserPhone(): Flow<String>

    fun getUserEmail(): Flow<String>

}