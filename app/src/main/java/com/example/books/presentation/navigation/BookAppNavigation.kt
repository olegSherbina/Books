package com.example.books.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.books.presentation.booklist.BookScreen

object Routes {
    const val BOOK_LIST = "book_list"
}

@Composable
fun BookAppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.BOOK_LIST) {
        composable(Routes.BOOK_LIST) {
            BookScreen(navController = navController)
        }
    }
}

