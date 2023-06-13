package com.markvtls.diploma_aug.domain.repository

import androidx.lifecycle.MutableLiveData
import com.markvtls.diploma_aug.domain.model.ImageModel

interface ImageRepository {

    suspend fun saveUserOrder(imageModel: ImageModel)

    suspend fun getUserImages(userIdentificator: String, listener: MutableLiveData<List<ImageModel>>)
}