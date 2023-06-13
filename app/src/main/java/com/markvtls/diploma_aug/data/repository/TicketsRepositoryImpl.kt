package com.markvtls.diploma_aug.data.repository

import androidx.lifecycle.MutableLiveData
import com.markvtls.diploma_aug.data.source.remote.FireBaseFirestore
import com.markvtls.diploma_aug.domain.model.ImageModel
import com.markvtls.diploma_aug.domain.repository.ImageRepository
import javax.inject.Inject

class TicketsRepositoryImpl @Inject constructor(
    private val firestore: FireBaseFirestore
): ImageRepository {
    override suspend fun saveUserOrder(imageModel: ImageModel) {
        firestore.saveImageOrderToDataBase(imageModel)
    }

    override suspend fun getUserImages(userIdentificator: String, listener: MutableLiveData<List<ImageModel>>){
        firestore.getUserOrders(listener)
    }
}