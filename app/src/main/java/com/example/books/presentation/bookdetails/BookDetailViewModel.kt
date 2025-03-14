package com.example.books.presentation.bookdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.domain.model.Book
import com.example.books.domain.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    data class BookDetailState(
        val book: Book? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _state = MutableStateFlow(BookDetailState(isLoading = true))
    val state: StateFlow<BookDetailState> = _state.asStateFlow()

    fun loadBookDetails(bookId: String) {
        viewModelScope.launch {
            val result = bookRepository.getBookDetails(bookId)
            _state.value = when {
                result.isSuccess -> BookDetailState(book = result.getOrNull(), isLoading = false)
                result.isFailure -> BookDetailState(
                    error = result.exceptionOrNull()?.message ?: "Unknown error",
                    isLoading = false
                )
                else -> BookDetailState(isLoading = false)
            }
        }
    }
}

/*import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.domain.model.Book
import com.example.books.domain.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    data class BookDetailState(
        val book: Book? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _state = MutableStateFlow(BookDetailState())
    val state: StateFlow<BookDetailState> = _state.asStateFlow()

    fun loadBookDetails(bookId: String) {
        viewModelScope.launch {
            _state.value = BookDetailState(isLoading = true)
            val result = bookRepository.getBookDetails(bookId)
            _state.value = when {
                result.isSuccess -> BookDetailState(book = result.getOrNull())
                result.isFailure -> BookDetailState(error = result.exceptionOrNull()?.message ?: "Unknown error")
                else -> BookDetailState()
            }
        }
    }
}*/
