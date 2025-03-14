package com.example.books.presentation.booklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.domain.model.Book
import com.example.books.domain.usecase.SearchBooksUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BookViewModel(
    private val searchBooksUseCase: SearchBooksUseCase
) : ViewModel() {

    data class BookState(
        val books: List<Book> = emptyList(),
        val searchQuery: String = "",
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed class NavigationEvent {
        data class NavigateToBookDetails(val bookId: String) : NavigationEvent()
    }

    private val _state = MutableStateFlow(BookState())
    val state: StateFlow<BookState> = _state.asStateFlow()

    private val _navigationEvents = Channel<NavigationEvent>(Channel.BUFFERED)
    val navigationEvents: Flow<NavigationEvent> = _navigationEvents.receiveAsFlow()

    private val searchQueryFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            searchQueryFlow
                .debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    searchBooks(query)
                }
        }
    }

    fun onEvent(event: BookEvent) {
        when (event) {
            is BookEvent.SearchQueryChanged -> {
                _state.value = _state.value.copy(searchQuery = event.query)
                searchQueryFlow.value = event.query
            }
            is BookEvent.BookClicked -> {
                viewModelScope.launch {
                    _navigationEvents.send(NavigationEvent.NavigateToBookDetails(event.bookId))
                }
            }
        }
    }

    private suspend fun searchBooks(query: String) {
        if (query.isNotBlank()) {
            _state.value = _state.value.copy(isLoading = true)
            searchBooksUseCase(query).collect { result ->
                _state.value = _state.value.copy(
                    books = result.getOrNull() ?: emptyList(),
                    error = result.exceptionOrNull()?.message,
                    isLoading = false
                )
            }
        } else {
            _state.value = _state.value.copy(books = emptyList(), isLoading = false)
        }
    }
}
