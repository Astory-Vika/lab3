package com.example.task2.data

import com.google.gson.annotations.SerializedName

data class ApiListResponse<T>(
    @SerializedName("data")
    val data: List<T>,
    @SerializedName("pagination")
    val pagination: ApiPagination,
)

data class ApiResponse<T>(
    @SerializedName("data")
    val data: T,
)

data class ApiPagination(
    @SerializedName("last_visible_page")
    val lastPage: Int,
    @SerializedName("has_next_page")
    val hasNext: Boolean,
)

data class Anime(
    @SerializedName("mal_id")
    val id: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("year")
    val year: Int?,
    @SerializedName("synopsis")
    val synopsis: String?,
    @SerializedName("status")
    val status: String,
    @SerializedName("images")
    val images:ApiImageMap,
    @SerializedName("score")
    val score: Float?,
)

data class ApiImageMap(
    @SerializedName("jpg")
    val jpg: ApiImage,
    @SerializedName("webp")
    val webp: ApiImage,
)

data class ApiImage(
    @SerializedName("image_url")
    val url: String,
    @SerializedName("small_image_url")
    val smallUrl: String,
    @SerializedName("large_image_url")
    val largeUrl: String,
)

enum class ApiSearchOrder(val value: String) {
    SCORE("score"),
    TITLE("title"),
    START_DATE("start_date"),
}