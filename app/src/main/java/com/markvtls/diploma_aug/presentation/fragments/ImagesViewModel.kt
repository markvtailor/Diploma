package com.markvtls.diploma_aug.presentation.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markvtls.diploma_aug.domain.model.ImageModel
import com.markvtls.diploma_aug.domain.usecases.tickets.GetUserImagesUseCase
import com.markvtls.diploma_aug.domain.usecases.tickets.SaveUserImageUseCase
import com.markvtls.diploma_aug.presentation.PaymentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getUserImagesUseCase: GetUserImagesUseCase,
    private val saveUserImageUseCase: SaveUserImageUseCase
): ViewModel() {

    sealed class Event {
        object FeedEnd : Event()
        object ConnectionError : Event()
        object ServerError : Event()

    }

    var lastImageName: String = ""
    var lastImageAuthor: String = ""
    var lastImageSize: String = ""
    var lastImageUri: String = ""
    var lastImagePrice = 0


    val lastImagePaymentType: MutableLiveData<PaymentType> by lazy {
        MutableLiveData<PaymentType>(PaymentType.MASTERCARD)
    }




    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private val _currentImages: MutableLiveData<List<ImageModel>> by lazy {
        MutableLiveData<List<ImageModel>>()
    }

    val currentImages: LiveData<List<ImageModel>> get() = _currentImages


    fun loadUserImages(userIdentificator: String?) {
        if (userIdentificator != null) {
            viewModelScope.launch(Dispatchers.IO + SupervisorJob()) {
                try {
                    getUserImagesUseCase(userIdentificator, _currentImages)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun saveUserOrder() {
        viewModelScope.launch(Dispatchers.IO + SupervisorJob()) {
            try {
                val currentImage = ImageModel(
                    imageName = lastImageName,
                    imageAuthor = lastImageAuthor,
                    imageSize = lastImageSize,
                    imagePrice = lastImagePrice,
                    imageUri = lastImageUri,
                    isDelivered = false
                )

                saveUserImageUseCase(currentImage)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}