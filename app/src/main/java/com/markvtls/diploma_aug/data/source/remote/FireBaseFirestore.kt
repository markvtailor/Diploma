package com.markvtls.diploma_aug.data.source.remote

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.markvtls.diploma_aug.domain.model.ImageModel
import java.util.*


class FireBaseFirestore {

    private var db = FirebaseFirestore.getInstance()

    fun saveImageOrderToDataBase(model: ImageModel) {

        val user = Firebase.auth.currentUser

        val userIdentificator =
            if (user?.email != null) {
                user.email
            } else user?.phoneNumber


        val ticketData = hashMapOf(
            "imageName" to model.imageName,
            "imageAuthor" to model.imageAuthor,
            "imageSize" to model.imageSize,
            "imagePrice" to model.imagePrice,
            "imageUri" to model.imageUri,
            "isDelivered" to model.isDelivered
        )

        if (userIdentificator != null) {
            db.collection(userIdentificator)
                .document("ImageOrder ${UUID.randomUUID()}")
                .set(ticketData)
        }
    }

    fun getUserOrders(listener: MutableLiveData<List<ImageModel>>) {

        val user = Firebase.auth.currentUser

        val userIdentificator =
            if (user?.email != null) {
                user.email
            } else user?.phoneNumber

        if (userIdentificator != null) {
            db.collection(userIdentificator)
                .get()
                .addOnSuccessListener { documents->
                    val userOrders = mutableListOf<ImageModel>()
                    for (document in documents) {
                        val imageOrder = document.toObject(ImageModel::class.java)
                        userOrders.add(imageOrder)
                    }

                    listener.value = userOrders
                }
        }
    }
}