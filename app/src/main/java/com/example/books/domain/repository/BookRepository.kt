package com.example.books.domain.repository

import com.example.books.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(query: String): Flow<Result<List<Book>>>
    //TODO suspend fun getBookDetails(bookId: String): Result<Book>
}