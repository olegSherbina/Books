package com.example.books.data.api

import com.example.books.data.model.AuthorResponse
import com.example.books.data.model.BookDetailResponse
import com.example.books.data.model.BookSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenLibraryApi {
    @GET("/search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("page") page: Int = 1
    ): Response<BookSearchResponse>

    @GET("/works/{workId}.json")
    suspend fun getBookDetails(
        @Path("workId") workId: String
    ): Response<BookDetailResponse>

    @GET("authors/{authorId}.json")
    suspend fun getAuthorDetails(
        @Path("authorId") authorId: String
    ): Response<AuthorResponse>
}