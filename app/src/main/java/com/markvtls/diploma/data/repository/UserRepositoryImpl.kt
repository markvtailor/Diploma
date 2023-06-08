package com.markvtls.diploma.data.repository

import com.markvtls.diploma.data.source.local.UserInfoStore
import com.markvtls.diploma.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val userInfoStore: UserInfoStore
): UserRepository {

    override suspend fun saveUserPhone(userPhone: String) {
        userInfoStore.saveUserPhone(userPhone)
    }

    override suspend fun saveUserEmail(userEmail: String) {
        userInfoStore.saveUserEmail(userEmail)
    }

    override fun getUserPhone(): Flow<String> {
        return userInfoStore.userPhoneFlow
    }

    override fun getUserEmail(): Flow<String> {
        return userInfoStore.userEmailFlow
    }

}