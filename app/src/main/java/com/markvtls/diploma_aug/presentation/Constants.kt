package com.markvtls.diploma_aug.presentation

import com.markvtls.diploma_aug.domain.model.ImageModel


enum class PaymentType {
    MASTERCARD,
    VISA,
    GPAY,
    NEW
}


enum class Images( val model: String, val uri: String) {
    FIRST("models/1.glb", "https://guardian.ng/wp-content/uploads/2022/12/African-art-scaled.jpeg"),
    SECOND("models/2.glb","https://www.bworldonline.com/wp-content/uploads/2022/10/Digital-art.jpg"),
    THIRD("models/3.glb","https://arts.mojarto.com/siteImages/category-99538-Painting.jpeg"),
    FOURTH("models/4.glb","https://www.artsacad.net/wp-content/uploads/2023/03/girl-2696947_1280.jpg"),
    FIFTH("models/5.glb", "https://www.pinotandpicasso.com.au/gladesville/wp-content/uploads/2023/03/Textured-Art-Seas-The-Day.jpg")

}

val testList = listOf(
    ImageModel("Красивое 1", "Нейропикассо",Images.FIRST.uri,1000, false, "Small"),
    ImageModel("Красивое 2", "Нейродали",Images.SECOND.uri,1000, false, "Small"),
    ImageModel("Красивое 3", "НейроВанГог",Images.THIRD.uri,1000, false, "Small"),
    ImageModel("Красивое 4", "Нейрорафаэль",Images.FOURTH.uri,1000, false, "Small"),
    ImageModel("Красивое 5", "Нейромоне",Images.FIFTH.uri,1000, false, "Small"),

    )