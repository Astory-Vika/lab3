package com.example.task2.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task2.data.AnimeRepository
import com.example.task2.ui.states.AnimeUiState
import com.example.task2.ui.states.BaseAnimeData
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AnimeViewModel : ViewModel() {
    private val repository: AnimeRepository = AnimeRepository()

    private val _uiState = MutableStateFlow(
        AnimeUiState(
            BaseAnimeData(null, null, null, null, null),
            null,
            isLoading = false,
        )
    )
    val uiState: StateFlow<AnimeUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun setBaseData(baseAnimeData: BaseAnimeData) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    baseAnimeData = baseAnimeData
                )
            }
        }
    }

    fun fetchAnime() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            uiState.value.baseAnimeData.id?.let { id ->
                _uiState.update {
                    it.copy(isLoading = true)
                }
                try {
                    val anime =
                        repository.fetchAnime(id)
                    _uiState.update {
                        it.copy(
                            anime =anime.data,
                            isLoading = false,
                        )
                    }

                } catch (e: Exception) {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}