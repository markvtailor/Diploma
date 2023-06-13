package com.markvtls.diploma_aug.domain.usecases.tickets

import com.markvtls.diploma_aug.domain.model.ImageModel
import com.markvtls.diploma_aug.domain.repository.ImageRepository
import javax.inject.Inject

class SaveUserImageUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(imageModel: ImageModel) {
        repository.saveUserOrder(imageModel)
    }
}