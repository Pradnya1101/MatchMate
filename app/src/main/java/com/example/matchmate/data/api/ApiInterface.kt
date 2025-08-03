package com.example.matchmate.data.api

import com.example.matchmate.data.model.UserResponse
import retrofit2.http.GET

interface ApiInterface {
    @GET("api/?results=10")
    suspend fun getUsers(): UserResponse
}
