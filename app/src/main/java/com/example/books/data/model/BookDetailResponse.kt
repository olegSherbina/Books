package com.example.books.data.model

import com.google.gson.annotations.SerializedName

data class BookDetailResponse(
    @SerializedName("title")
    val title: String?,

    @SerializedName("authors")
    val authors: List<Author>?,

    @SerializedName("created")
    val created: CreationDate?,

    @SerializedName("covers")
    val covers: List<Int>?,

    @SerializedName("key")
    val key: String?
) {
    data class Author(
        @SerializedName("author")
        val author: AuthorKey?
    ) {
        data class AuthorKey(
            @SerializedName("key")
            val key: String?
        )
    }

    data class CreationDate(
        @SerializedName("value")
        val value: String?
    )
}
