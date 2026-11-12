package com.practice.task.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.task.models.UiState
import com.practice.task.repositories.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val _restaurants : MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val restaurants = _restaurants.asStateFlow()

    fun getData(token:String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            homeRepository.getRestaurants(token).catch {
                _restaurants.value = UiState.Error(it.message)
            }.collect{
                _restaurants.value = it
            }
        }
    }
}