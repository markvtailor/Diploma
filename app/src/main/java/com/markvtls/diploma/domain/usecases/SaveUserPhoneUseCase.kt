package com.markvtls.diploma.domain.usecases

import com.markvtls.diploma.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserPhoneUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userPhone: String) {
        userRepository.saveUserPhone(userPhone)
    }
}