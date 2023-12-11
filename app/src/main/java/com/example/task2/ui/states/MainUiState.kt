package com.example.task2.ui.states

import com.example.task2.data.Anime

data class MainUiState (
    val animeList: List<Anime>,
    val isLoading: Boolean,
    val lastPageLoaded: Int,
)