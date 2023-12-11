package com.example.task2.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimeRepository {
    private val apiClient = getApiInstance()

    suspend fun fetchCurrentSeason(limit: Int, page: Int): ApiListResponse<Anime> {
        return withContext(Dispatchers.IO) {
            return@withContext apiClient.getCurrentSeason(page, limit)
        }
    }

    suspend fun fetchAnime(id: Int): ApiResponse<Anime> {
        return withContext(Dispatchers.IO) {
            return@withContext apiClient.getAnimeById(id)
        }
    }

}