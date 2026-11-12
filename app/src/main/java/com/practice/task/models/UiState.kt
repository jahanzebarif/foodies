package com.practice.task.models

sealed class UiState {
    data class Data(val data:Any) : UiState()
    object Loading : UiState()
    data class Error(val error:String?) : UiState()
}