package com.example.fetchapp.ui.fetchScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchapp.data.FetchItem
import com.example.fetchapp.repository.FetchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing UI-related data for fetching and displaying items.
 *
 * This ViewModel interacts with [FetchRepository] to retrieve data and exposes it to the UI.
 * It handles the state of the fetched items and manages the data fetching process.
 *
 * @property repository An instance of [FetchRepository] used to fetch and process items.
 */
@HiltViewModel
class FetchViewModel @Inject constructor(
    private val repository: FetchRepository
) : ViewModel() {

    /**
     * Holds the state of the fetched items grouped by [FetchItem.listId].
     *
     * This state is observed by the UI to display the list of items.
     * The map's key is the `listId`, and the value is a list of [FetchItem] objects associated with that `listId`.
     *
     * @see FetchItemsScreen
     */
    var items by mutableStateOf<Map<Int, List<FetchItem>>>(emptyMap())
        private set

    /**
     * Initiates the fetching of items from the repository.
     *
     * This function launches a coroutine in the [viewModelScope] to perform the network request asynchronously.
     * Upon successful data retrieval, it updates the [items] state with the grouped and sorted items.
     * If an exception occurs during the fetching process, it logs the error message.
     *
     * @see FetchRepository.getGroupedAndSortedItems
     */
    fun fetchItems() {
        viewModelScope.launch {
            try {
                // Fetch and update grouped and sorted items
                items = repository.getGroupedAndSortedItems()
            } catch (e: Exception) {
                Log.d("billy", "Failed to fetch items: ${e.message}")
            }
        }
    }
}