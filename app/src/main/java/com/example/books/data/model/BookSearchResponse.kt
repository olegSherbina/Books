package com.example.books.data.model

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