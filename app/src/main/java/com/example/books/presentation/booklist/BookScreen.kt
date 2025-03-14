package com.example.books.presentation.booklist


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.books.R
import com.example.books.domain.model.Book
import com.example.books.presentation.navigation.Routes
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookScreen(
    viewModel: BookViewModel = koinViewModel(),
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value
    //val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            when (event) {
                is BookViewModel.NavigationEvent.NavigateToBookDetails -> {
                    navController.navigate(Routes.bookDetails(event.bookId))
                }
            }
        }
    }

    Column {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { viewModel.onEvent(BookEvent.SearchQueryChanged(it)) },
            label = { Text("Search books") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    //focusManager.clearFocus()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.search_field_padding))
        )

        LazyColumn {
            items(state.books) { book ->
                BookItem(
                    book = book,
                    onClick = { viewModel.onEvent(BookEvent.BookClicked(book.id)) }
                )
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Composable
fun BookItem(book: Book, onClick: () -> Unit) {
    println("Loading cover: ${book.coverUrl}")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(dimensionResource(R.dimen.book_item_card_padding))
    ) {
        Row {
            AsyncImage(
                model = book.coverUrl,
                contentDescription = "Book cover",
                modifier = Modifier
                    .size(dimensionResource(R.dimen.book_item_cover_size))
                    .padding(dimensionResource(R.dimen.book_item_image_vertical_padding))
            )
            Column(modifier = Modifier.padding(dimensionResource(R.dimen.book_item_text_padding))) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(book.author, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
