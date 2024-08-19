package com.example.quiz_app.repository

import android.util.Log
import com.example.quiz_app.data.DataorException
import com.example.quiz_app.model.QuestionItem
import com.example.quiz_app.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionApi) {
    private val dataorException = DataorException<ArrayList<QuestionItem>, Boolean, Exception>()
    suspend fun getAllFunction(): DataorException<ArrayList<QuestionItem>, Boolean,java.lang.Exception>
    {
        try {
            dataorException.loading=true
            dataorException.data=api.getAllQuestion()
            if(dataorException.data.toString().isNotEmpty()) dataorException.loading=false

        }catch (exception:Exception){
            dataorException.exception=exception
            Log.d("error", "getAllFunction: ${dataorException.exception!!.localizedMessage}")
        }
        return dataorException
    }
}