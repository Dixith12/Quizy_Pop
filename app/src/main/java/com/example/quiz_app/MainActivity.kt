package com.example.quiz_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quiz_app.screen.QuestionViewmodel
import com.example.quiz_app.ui.theme.Quiz_AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Quiz_AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TriviaHome()
                }
            }
        }
    }
}

@Composable
fun TriviaHome(viewmodel: QuestionViewmodel = hiltViewModel()){
    Questions(viewmodel)
}

@Composable
fun Questions(viewmodel: QuestionViewmodel) {
    val questions= viewmodel.data.value.data?.toMutableList()
    Log.d("size","Questions: ${questions?.size}")
}

