package com.example.quiz_app.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz_app.model.QuestionItem
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

@Composable
fun QuestionsPreview(question:QuestionItem,
                     questionIndex:MutableState<Int>,
                     questionViewmodel: QuestionViewmodel,
                     onNextClicked:(Int)->Unit
                     ) {
val choicesState = remember(question) {
    question.choices.toMutableList()
}

    val pathEffect=PathEffect.dashPathEffect(floatArrayOf(10f,10f),0f)
    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(4.dp), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top) {

            QuestionTracker()
            drawdotted(pathEffect)
            Column {
                Text("What is the meaning of all that?",
                    modifier = Modifier
                        .padding(6.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.2f),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp)
                choicesState.forEachIndexed { index, answerText ->
                    Row(modifier = Modifier.padding(3.dp)
                        .fillMaxWidth()
                        .height(45.dp)
                        .border(width = 4.dp, brush = Brush.linearGradient(colors = listOf(Color.Blue,Color.Black))
                            ,shape= RoundedCornerShape(15.dp)).clip(RoundedCornerShape(topStartPercent = 50, topEndPercent = 50, bottomStartPercent = 50, bottomEndPercent = 50))
                        .background(Color.Transparent)) {

                    }
                }
            }
        }

    }
}

@Composable
fun drawdotted(pathEffect: PathEffect){
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)) {
        drawLine(
            color = Color.Black,
            start = Offset(0f, 0f),
            end = Offset(size.width, y =0f),
            pathEffect = pathEffect
        )
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



