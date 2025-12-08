package com.example.messenger.data.api

import com.example.messenger.data.model.Message
import retrofit2.http.GET

interface MessageApi {
    @GET("posts")
    suspend fun getMessages(): List<Message>
}
