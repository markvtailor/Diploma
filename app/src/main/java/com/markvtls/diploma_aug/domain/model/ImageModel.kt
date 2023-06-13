package com.markvtls.diploma_aug.domain.model

data class ImageModel(
    var imageName: String = "",
    var imageAuthor: String = "",
    var imageUri: String = "",
    var imagePrice: Int = 0,
    var isDelivered: Boolean = false,
    var imageSize: String = ""
)
