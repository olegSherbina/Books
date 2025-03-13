package com.example.books.di

import com.example.books.BuildConfig
import com.example.books.data.api.OpenLibraryApi
import com.example.books.domain.repository.BookRepository
import com.example.books.domain.repository.BookRepositoryImpl
import com.example.books.domain.usecase.SearchBooksUseCase
import com.example.books.presentation.booklist.BookViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { Retrofit.Builder()
        .baseUrl(BuildConfig.OPEN_LIBRARY_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OpenLibraryApi::class.java)
    }

    single<BookRepository> { BookRepositoryImpl(get()) }
    single { SearchBooksUseCase(get()) }
    viewModel { BookViewModel(get()) }
}