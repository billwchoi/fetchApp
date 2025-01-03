package com.example.fetchapp.data

import retrofit2.Response
import retrofit2.http.GET

interface FetchApi {
    @GET("hiring.json")
    suspend fun getItems(): Response<List<FetchItem>>
}