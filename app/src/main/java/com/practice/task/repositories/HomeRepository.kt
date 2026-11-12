package com.practice.task.repositories

import com.practice.task.models.UiState
import com.practice.task.network.API
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepository(private val api:API) {

    fun getRestaurants(token:String) : Flow<UiState>
    {
        return flow {
            emit(UiState.Data(api.getRestaurants(token)))
        }
    }

}