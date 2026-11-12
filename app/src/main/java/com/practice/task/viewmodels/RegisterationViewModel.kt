package com.practice.task.viewmodels

import androidx.lifecycle.ViewModel
import com.practice.task.models.UiState
import com.practice.task.repositories.RegistrationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RegisterationViewModel(private val registrationRepository: RegistrationRepository) :
    ViewModel() {
    private val _registerResponse: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val registerResponse = _registerResponse.asStateFlow()

     suspend fun registerUser(
        name: RequestBody,
        email: RequestBody,
        phone: RequestBody,
        password: RequestBody,
        confirm_password: RequestBody,
        image: MultipartBody.Part
    ) {
        registrationRepository.registerUser(name, email, phone, password, confirm_password, image)
            .catch {
                _registerResponse.value = UiState.Error(it.cause?.message.toString())
            }.collect {
            _registerResponse.value = it
        }
    }
}