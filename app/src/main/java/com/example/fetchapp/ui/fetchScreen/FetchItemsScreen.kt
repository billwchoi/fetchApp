package com.example.fetchapp.ui.fetchScreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Displays a list of fetched items grouped by their list IDs.
 *
 * This composable observes the [FetchViewModel] for data changes and displays the items
 * in a vertically scrollable list. Each group of items is preceded by a header indicating
 * the `listId`.
 *
 * @param modifier Modifier to be applied to the LazyColumn.
 * @param viewModel An instance of [FetchViewModel] provided by Hilt for managing UI state.
 */
@Composable
fun FetchItemsScreen(
    modifier: Modifier = Modifier,
    viewModel: FetchViewModel = hiltViewModel()
) {
    val items = viewModel.items

    LaunchedEffect(Unit) {
        viewModel.fetchItems() // Fetch items when the screen is loaded
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(50.dp)
    ) {

        items.forEach { (listId, fetchItems) ->

            item {
                Spacer(modifier = Modifier.height(50.dp))
                Text(text = " List ID: $listId", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(10.dp))
            }
            items(fetchItems) { item ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "  ID: ${item.id}, Name: ${item.name}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
