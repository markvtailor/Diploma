package com.markvtls.diploma_aug.domain.usecases.tickets

import androidx.lifecycle.MutableLiveData
import com.markvtls.diploma_aug.domain.model.ImageModel
import com.markvtls.diploma_aug.domain.repository.ImageRepository
import javax.inject.Inject

class GetUserImagesUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(userIdentificator: String, listener: MutableLiveData<List<ImageModel>>) {
       repository.getUserImages(userIdentificator, listener)
    }
}