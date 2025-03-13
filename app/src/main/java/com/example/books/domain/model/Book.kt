package com.example.books.domain.model

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val coverUrl: String?,
    val year: String? = null
)