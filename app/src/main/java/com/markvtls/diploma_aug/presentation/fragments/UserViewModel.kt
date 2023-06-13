package com.markvtls.diploma_aug.presentation.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markvtls.diploma_aug.domain.usecases.userinfo.GetUserEmailUseCase
import com.markvtls.diploma_aug.domain.usecases.userinfo.GetUserPhoneUseCase
import com.markvtls.diploma_aug.domain.usecases.userinfo.SaveUserEmailUseCase
import com.markvtls.diploma_aug.domain.usecases.userinfo.SaveUserPhoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val getUserPhoneUseCase: GetUserPhoneUseCase,
    private val saveUserPhoneUseCase: SaveUserPhoneUseCase,
    private val saveUserEmailUseCase: SaveUserEmailUseCase
) : ViewModel() {

    private val _userPhone = MutableStateFlow("")
    val userPhone: StateFlow<String> get() = _userPhone

    private val _userEmail = MutableStateFlow("")
    val userEmail: StateFlow<String> get() = _userEmail


     val _isLogged: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(true)
    }

    val isLogged: LiveData<Boolean> get() = _isLogged

    init {
        getUserEmail()
        getUserPhone()
    }

    fun saveUserPhone(userPhone: String?) {
        if (userPhone != null)
            viewModelScope.launch(Dispatchers.IO) {
                saveUserPhoneUseCase(userPhone)
            }
    }

    fun saveUserEmail(userEmail: String?) {
        if (userEmail != null) {
            viewModelScope.launch(Dispatchers.IO) {
                saveUserEmailUseCase(userEmail)
            }
        }
    }

    fun getUserEmail() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserEmailUseCase().collect {
                _userEmail.value = it
            }
        }
    }

    fun getUserPhone() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserPhoneUseCase().collect {
                _userPhone.value = it
            }
        }
    }

}