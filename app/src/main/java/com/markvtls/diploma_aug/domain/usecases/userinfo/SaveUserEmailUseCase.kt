package com.markvtls.diploma_aug.domain.usecases.userinfo

import com.markvtls.diploma_aug.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserEmailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userEmail: String) {
        userRepository.saveUserEmail(userEmail)
    }
}