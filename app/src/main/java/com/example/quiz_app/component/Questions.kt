package com.example.quiz_app.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz_app.screen.QuestionViewmodel

@Composable
fun Questions(viewmodel: QuestionViewmodel) {
    val questions= viewmodel.data.value.data?.toMutableList()
    Log.d("size","Questions: ${questions?.size}")
    if(viewmodel.data.value.loading==true)
    {
        CircularProgressIndicator()
    }

}
@Preview
@Composable
fun QuestionsPreview() {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(4.dp), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top) {

            QuestionTracker()
        }

    }
}
@Composable
fun QuestionTracker(count:Int=10,outof:Int=100){
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)){
            withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 27.sp)){
                append(text = "Question $count")
                withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Light, fontSize = 14.sp)){
                    append(text = "/$outof")
                }
            }
        }
    }, modifier = Modifier.padding(20.dp))
}



