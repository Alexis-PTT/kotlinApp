package com.mainProject.Ui.ComposablesForPages

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mainProject.Db.DataClassGraphic.ExerciseHomeData
import com.mainProject.Db.DataClassGraphic.ExerciseRecordData
import com.mainProject.ViewModel.ViewModelMain
import org.intellij.lang.annotations.JdkConstants

class HomeExercices {

    @Composable
    fun listOfExercices(
        viewModel: ViewModelMain,
        navController: NavController
    ){
        var exercises by remember { mutableStateOf<List<ExerciseHomeData>>(emptyList()) }
        LaunchedEffect(Unit) {
            exercises = viewModel.getExercisesForHome()

        }
        //TODO : IMPLEMENT add some parameters to chose by muscle or muscle group
        // if you chose a muscleGroup only the muscles in this group will appear in the muscle selection
        // all by default
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.padding(8.dp).fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally){
                exercises.forEach{ exercise ->
                    item(){
                        bubbleExercice(exercise,navController)
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun bubbleExercice(exercise : ExerciseHomeData, navController :  NavController) {
            Card(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    navController.navigate("exerciseInfoPage/${exercise.exercise_id}")
                })
            {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(  modifier = Modifier
                        .width(290.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,){
                        Text(
                            exercise.name,
                            modifier = Modifier.padding(5.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left
                        )
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "delete the exercice",
                            modifier = Modifier.padding(5.dp)
                        )


                    }
                    Text(
                        "groupes musculaires touchée :",
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                    Text("  " + exercise.muscleGroup.joinToString(", "))
                    Text(
                        "niveau actuel :",
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                    Text("  set/reps : ${exercise.set_count}X${exercise.rep_count}")
                    Text("  poids : ${exercise.weight} KG")
                }

        }



    }
}