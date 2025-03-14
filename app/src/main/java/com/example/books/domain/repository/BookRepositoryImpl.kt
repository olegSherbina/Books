package com.example.books.domain.repository

import com.example.books.BuildConfig
import com.example.books.data.api.OpenLibraryApi
import com.example.books.data.model.BookDetailResponse
import com.example.books.data.model.BookItemResponse
import com.example.books.data.model.BookSearchResponse
import com.example.books.data.model.toDomain
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

    override suspend fun getBookDetails(bookId: String): Result<Book> {
        return try {
            val response = api.getBookDetails(bookId)
            if (response.isSuccessful) {
                val bookDetail = response.body()
                if (bookDetail != null) {
                    val authorId =
                        bookDetail.authors?.firstOrNull()?.author?.key?.substringAfterLast("/")
                    val authorResponse = authorId?.let { api.getAuthorDetails(it) }
                    val authorName = if (authorId != null && authorResponse?.isSuccessful == true) {
                        authorResponse.body()?.name
                    } else null
                    Result.success(bookDetail.toDomain(authorName ?: "Unknown Author"))
                } else {
                    Result.failure(Exception("Book details not found"))
                }
            } else {
                Result.failure(Exception("Details fetch failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
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

private fun BookDetailResponse.toDomain(): Book {
    return Book(
        id = key?.substringAfterLast("/") ?: "",
        title = title ?: "Unknown Title",
        author = authors?.firstOrNull()?.author?.key?.let { "Author: $it" } ?: "Unknown Author",
        coverUrl = covers?.firstOrNull()?.let { "${BuildConfig.COVER_BASE_URL}$it${BuildConfig.COVER_SIZE_LARGE}" }
    )
}

private fun BookDetailResponse.toDomain(authorName: String): Book {
    return Book(
        id = key?.substringAfterLast("/") ?: "",
        title = title ?: "Unknown Title",
        author = authorName,
        coverUrl = covers?.firstOrNull()?.let { "${BuildConfig.COVER_BASE_URL}$it${BuildConfig.COVER_SIZE_LARGE}" },
        year = created?.value?.substringBefore("-")
    )
}

