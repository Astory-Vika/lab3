package com.example.task2.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task2.data.AnimeRepository
import com.example.task2.ui.states.MainUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val repository: AnimeRepository = AnimeRepository()
    private val defaultLimit = 10

    private val _uiState = MutableStateFlow(MainUiState(listOf(), false, 0))
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun fetchCurrentSeasonAnimes() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {
                val newsItems = repository.fetchCurrentSeason(defaultLimit, _uiState.value.lastPageLoaded + 1)
                _uiState.update {
                    it.copy(
                        animeList = it.animeList + newsItems.data,
                        lastPageLoaded = it.lastPageLoaded + 1,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}