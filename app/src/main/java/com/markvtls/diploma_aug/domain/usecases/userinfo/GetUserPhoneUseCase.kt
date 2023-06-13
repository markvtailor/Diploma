package com.markvtls.diploma_aug.domain.usecases.userinfo

import com.markvtls.diploma_aug.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserPhoneUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<String> = userRepository.getUserPhone()
}