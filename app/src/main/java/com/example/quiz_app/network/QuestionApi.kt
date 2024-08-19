package com.example.quiz_app.network

import com.example.quiz_app.model.Question
import retrofit2.http.GET
import javax.inject.Singleton


@Singleton
interface QuestionApi {
    @GET("world.json")
    suspend fun getAllQuestion():Question
}