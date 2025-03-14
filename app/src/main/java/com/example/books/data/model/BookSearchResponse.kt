package com.example.books.data.model

import com.example.books.BuildConfig
import com.example.books.domain.model.Book
import com.google.gson.annotations.SerializedName

data class BookSearchResponse(
    @SerializedName("docs")
    val docs: List<BookItemResponse>
)

data class BookItemResponse(
    @SerializedName("key")
    val key: String,

    @SerializedName("title")
    val title: String?,

    @SerializedName("author_name")
    val author_name: List<String>?,

    @SerializedName("cover_i")
    val cover_i: Int?
)
fun BookItemResponse.toDomain(): Book {
    return Book(
        id = key?.substringAfterLast("/") ?: "",
        title = title ?: "Unknown Title",
        author = author_name?.firstOrNull()?.let { "Author: $it" } ?: "Unknown Author",
        coverUrl = cover_i?.let { "${BuildConfig.COVER_BASE_URL}$it${BuildConfig.COVER_SIZE_LARGE}" }
    )
}