package com.example.books.domain.repository

import com.example.books.BuildConfig
import com.example.books.data.api.OpenLibraryApi
import com.example.books.data.model.BookDetailResponse
import com.example.books.data.model.BookItemResponse
import com.example.books.domain.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BookRepositoryImpl(
    private val api: OpenLibraryApi
) : BookRepository {
    override suspend fun searchBooks(query: String): Flow<Result<List<Book>>> = flow {
        try {
            val response = api.searchBooks(query)
            if (response.isSuccessful) {
                val books = response.body()?.docs?.map { it.toDomain() } ?: emptyList()
                emit(Result.success(books))
            } else {
                emit(Result.failure(Exception("Search failed: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}

private fun BookItemResponse.toDomain(): Book {
    return Book(
        id = key.substringAfterLast("/"),
        title = title ?: "Unknown Title",
        author = author_name?.joinToString(", ") ?: "Unknown Author",
        coverUrl = cover_i?.let { "${BuildConfig.COVER_BASE_URL}$it${BuildConfig.COVER_SIZE_LARGE}" }
    )
}

