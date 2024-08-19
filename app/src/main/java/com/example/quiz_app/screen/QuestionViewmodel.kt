package com.example.quiz_app.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz_app.data.DataorException
import com.example.quiz_app.model.QuestionItem
import com.example.quiz_app.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuestionViewmodel @Inject constructor(private val repository: QuestionRepository):ViewModel(){
    val data:MutableState<DataorException<ArrayList<QuestionItem>,Boolean,Exception>> = mutableStateOf(DataorException(null,true,Exception("")))
   init {
       getAllQuestions()
   }

    private fun getAllQuestions(){
        viewModelScope.launch {
            data.value.loading=true
            data.value=repository.getAllFunction()
            if(data.value.data.toString().isNotEmpty()) data.value.loading=false
        }
    }
}