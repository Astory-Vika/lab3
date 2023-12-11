package com.example.task2.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {
    @GET("v4/seasons/now")
    suspend fun getCurrentSeason(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): ApiListResponse<Anime>


    @GET("v4/anime/{id}")
    suspend fun getAnimeById(
        @Path("id") id: Int,
    ): ApiResponse<Anime>
}

fun getApiInstance(): AnimeApi {
    val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    val httpClient = OkHttpClient
        .Builder()
        .addInterceptor(loggingInterceptor)
        .build()


    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.jikan.moe/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
    return retrofit.create(AnimeApi::class.java)
}