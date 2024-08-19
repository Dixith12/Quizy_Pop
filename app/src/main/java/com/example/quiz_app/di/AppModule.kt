package com.example.quiz_app.di

import com.example.quiz_app.network.QuestionApi
import com.example.quiz_app.repository.QuestionRepository
import com.example.quiz_app.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(Singleton::class)
object AppModule {
    @Singleton
    @Provides
    fun provideQuestionRepository(api: QuestionApi)= QuestionRepository(api)

    @Singleton
    @Provides
    fun provideQuestionApi (): QuestionApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApi::class.java)
    }
}