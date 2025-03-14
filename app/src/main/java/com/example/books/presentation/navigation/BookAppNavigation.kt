package com.example.books.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.books.presentation.bookdetails.BookDetailScreen
import com.example.books.presentation.booklist.BookScreen

object Routes {
    const val BOOK_LIST = "book_list"
    const val BOOK_DETAILS = "book_details/{bookId}"

    fun bookDetails(bookId: String) = "book_details/$bookId"
}

@Composable
fun BookAppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.BOOK_LIST) {
        composable(Routes.BOOK_LIST) {
            BookScreen(navController = navController)
        }
        composable(
            route = Routes.BOOK_DETAILS,
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
            BookDetailScreen(bookId = bookId)
        }
    }
}

