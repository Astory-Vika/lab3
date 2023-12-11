package com.example.task2.ui.states

import com.example.task2.data.Anime

data class AnimeUiState(
    val baseAnimeData: BaseAnimeData,
    val anime: Anime?,
    val isLoading: Boolean,
)

data class BaseAnimeData(
    val id: Int?,
    val title: String?,
    val description: String?,
    val image: String?,
    var score: Float?

)
