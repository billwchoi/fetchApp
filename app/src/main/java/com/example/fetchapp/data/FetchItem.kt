package com.example.fetchapp.data

import com.google.gson.annotations.SerializedName


data class FetchItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("listId")
    val listId: Int,

    @SerializedName("name")
    val name: String?
)
