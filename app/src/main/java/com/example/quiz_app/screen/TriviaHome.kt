package com.example.quiz_app.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quiz_app.component.Questions

@Composable
fun TriviaHome(viewmodel: QuestionViewmodel = hiltViewModel()){
    Questions(viewmodel)
}
