package com.example.books.domain.usecase

import com.example.books.domain.model.Book
import com.example.books.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class SearchBooksUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(query: String): Flow<Result<List<Book>>> {
        return repository.searchBooks(query)
    }
}