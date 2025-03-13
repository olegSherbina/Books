package com.example.books.presentation.booklist

sealed class BookEvent {
    data class SearchQueryChanged(val query: String) : BookEvent()
    data class BookClicked(val bookId: String) : BookEvent()
}