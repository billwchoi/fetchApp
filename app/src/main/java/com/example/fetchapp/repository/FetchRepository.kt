package com.example.fetchapp.repository

import android.util.Log
import com.example.fetchapp.data.FetchApi
import com.example.fetchapp.data.FetchItem
import javax.inject.Inject

/**
 * Repository responsible for fetching and processing items from the Fetch API.
 *
 * @property fetchApi An instance of [FetchApi] used to make network requests.
 */
class FetchRepository @Inject constructor(
    private val fetchApi: FetchApi
) {
    /**
     * Fetches items from the Fetch API.
     *
     * This function makes a network call to retrieve a list of [FetchItem] objects.
     * It filters out any items where the [FetchItem.name] is null or empty.
     *
     * @return A [List] of valid [FetchItem] objects. Returns an empty list if the response body is null.
     * @throws Exception If the network request is unsuccessful, including the HTTP status code and message.
     */
    private suspend fun getItems(): List<FetchItem> {
        val response = fetchApi.getItems()
        if (response.isSuccessful) {
            // Null check and return only valid items
            return response.body()?.filter { !it.name.isNullOrEmpty() } ?: emptyList()
        } else {
            throw Exception("Failed to fetch items: ${response.code()} - ${response.message()}")
        }
    }

    /**
     * Retrieves items grouped by [FetchItem.listId] and sorted by [FetchItem.id].
     *
     * This function first fetches the items using [getItems], then groups them by their [FetchItem.listId].
     * The groups are sorted by the `listId` in ascending order, and within each group, the items are sorted by their `id`.
     * Additionally, the grouped and sorted items are logged for debugging purposes.
     *
     * @return A [Map] where the key is the `listId` and the value is a list of [FetchItem] objects sorted by `id`.
     * @throws Exception If fetching items fails due to a network error or unsuccessful response.
     */
    suspend fun getGroupedAndSortedItems(): Map<Int, List<FetchItem>> {
        val items = getItems()

        val groupedItem = items.groupBy { it.listId }
            .toSortedMap()
            .mapValues { (_, groupItems) ->
                groupItems.sortedBy { it.id }
            }
        groupedItem.forEach {
            Log.d("billy", "Grouped: $it")
        }
        return groupedItem
    }
}