package com.practice.task.repositories

import com.practice.task.models.UiState
import com.practice.task.network.API
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RegistrationRepository(private val api: API) {

    fun registerUser(
        name: RequestBody,
        email: RequestBody,
        phone: RequestBody,
        password: RequestBody,
        confirm_password: RequestBody,
        image: MultipartBody.Part
    ): Flow<UiState> {
        return flow {
            emit(UiState.Data(api.registerUser(name, email, phone, password, confirm_password, image)))
        }
    }
}