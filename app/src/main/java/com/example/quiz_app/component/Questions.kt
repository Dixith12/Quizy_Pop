package com.example.quiz_app.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.quiz_app.model.QuestionItem
import com.example.quiz_app.screen.QuestionViewmodel

@Composable
fun Questions(viewmodel: QuestionViewmodel) {
    val questions= viewmodel.data.value.data?.toMutableList()
    val questionIndex = remember {
        mutableStateOf(0)
    }
    Log.d("size","Questions: ${questions?.size}")
    if(viewmodel.data.value.loading==true)
    {
        CircularProgressIndicator()
    }
    else
    {
        val question=try {
            questions?.get(questionIndex.value)

        }
        catch (ex:Exception)
        {
            null
        }
          if(questions!=null)
          {
              QuestionsPreview(question = question!!, questionIndex = questionIndex, questionViewmodel = viewmodel)
              {
                  questionIndex.value=questionIndex.value+1
              }
          }
    }

}
@Composable
fun QuestionsPreview(question:QuestionItem,
                     questionIndex:MutableState<Int>,
                     questionViewmodel: QuestionViewmodel,
                     onNextClicked:(Int)->Unit={}
                     ) {
val choicesState = remember(question) {
    question.choices.toMutableList()
}
    var answerState by remember(question){
        mutableStateOf<Int?>(null)
    }
    var correctAnswerState = remember(question){
        mutableStateOf<Boolean?>(null)
    }
 val updateAnswer:(Int)->Unit= remember(question)
 {
     {

         answerState=it
         correctAnswerState.value=choicesState[it]==question.answer
     }
 }
    val pathEffect=PathEffect.dashPathEffect(floatArrayOf(10f,10f),0f)
    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(4.dp), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top) {

            if (questionIndex.value>3) Progress(questionIndex.value)
            QuestionTracker(count = questionIndex.value, outof = questionViewmodel.getsize())
            drawdotted(pathEffect)
            Column {
                Text(question.question,
                    modifier = Modifier
                        .padding(6.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.2f),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp)
                choicesState.forEachIndexed { index, answerText ->
                    Row(modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .height(45.dp)
                        .border(
                            width = 4.dp,
                            brush = Brush.linearGradient(colors = listOf(Color.Blue, Color.Black)),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clip(
                            RoundedCornerShape(
                                topStartPercent = 50,
                                topEndPercent = 50,
                                bottomStartPercent = 50,
                                bottomEndPercent = 50
                            )
                        )
                        .background(Color.Transparent)) {
                        RadioButton(selected = (answerState==index), onClick = {
                            updateAnswer(index)
                        }, modifier = Modifier.padding(start = 16.dp), colors = RadioButtonDefaults.colors(selectedColor =
                        if(correctAnswerState.value==true && index==answerState)
                        {
                            Color.Green.copy(0.2f)
                        } else {
                            Color.Red.copy(0.2f)
                        }
                        ))

                        val annotedString = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Light,
                                color=if(correctAnswerState.value==true && index==answerState)
                                {
                                    Color.Green
                                } else if(correctAnswerState.value==false && index==answerState)
                                {
                                    Color.Red
                                }
                            else{
                                    Color.Black

                            }, fontSize = 17.sp))
                            {
                                append(answerText)
                            }
                        }
                        Text(text = annotedString, modifier = Modifier.padding(6.dp))
                    }
                }
                Button(onClick = { onNextClicked(questionIndex.value) },
                    modifier = Modifier
                        .padding(4.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA59A38))
                ) {
                    Text(text = "Next", modifier = Modifier.padding(6.dp), color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.Bold)
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
fun QuestionTracker(count:Int,outof:Int){
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

@Preview
@Composable
fun Progress(score:Int=12)
{
    val progressScore = remember(score) {
        mutableStateOf(score*0.005f)
    }
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth()
        .height(45.dp)
        .border(
            width = 4.dp,
            brush = Brush.linearGradient(listOf(Color.White, Color.Black)),
            shape = RoundedCornerShape(34.dp)
        )
        .clip(
            RoundedCornerShape(
                topStartPercent = 50,
                topEndPercent = 50,
                bottomStartPercent = 50,
                bottomEndPercent = 50
            )
        )
        .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically

    ) {
        val gradient = Brush.linearGradient(listOf(Color.Black, Color.Magenta))
        Button(onClick = {},contentPadding = PaddingValues(1.dp),
            modifier = Modifier
                .fillMaxWidth(progressScore.value)
                .background(brush = gradient)
        , enabled = false,
            elevation = null,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, disabledContainerColor = Color.Transparent)
        ) {
            Text(text = (score*10).toString(),
                modifier = Modifier
                    .clip(RoundedCornerShape(23.dp))
                    .fillMaxHeight(0.87f)
                    .fillMaxWidth()
                    .padding(6.dp),
                color = Color.White,
                textAlign = TextAlign.Center
                    )
        }

    }
}



