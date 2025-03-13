package com.example.books.presentation.booklist

import com.example.books.domain.model.Book

data class BookState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)