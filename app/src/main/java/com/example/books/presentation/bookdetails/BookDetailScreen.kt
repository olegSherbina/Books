package com.example.books.presentation.bookdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.books.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookDetailScreen(
    bookId: String,
    viewModel: BookDetailViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(bookId) {
        viewModel.loadBookDetails(bookId)
    }

    BookDetailContent(state)
}

@Composable
fun BookDetailContent(state: BookDetailViewModel.BookDetailState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(R.dimen.book_detail_box_padding)),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text(
                    text = "Error: ${state.error}",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            state.book != null -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.book_detail_column_spacing))
                ) {
                    AsyncImage(
                        model = state.book.coverUrl,
                        contentDescription = "Book cover",
                        modifier = Modifier.size(dimensionResource(R.dimen.book_detail_cover_size))
                    )
                    Text(state.book.title, style = MaterialTheme.typography.headlineMedium)
                    Text(state.book.author, style = MaterialTheme.typography.bodyLarge)
                    state.book.year?.let {
                        Text("Year: $it", style = MaterialTheme.typography.bodyMedium)
                    } ?: Text("Year: Unknown", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
