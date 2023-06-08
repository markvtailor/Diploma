package com.markvtls.diploma.domain.usecases

import com.markvtls.diploma.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserEmailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<String> = userRepository.getUserEmail()
}