package com.example.books.data.model

import com.google.gson.annotations.SerializedName

data class AuthorResponse(
    @SerializedName("key")
    val key: String?,
    @SerializedName("name")
    val name: String?
)