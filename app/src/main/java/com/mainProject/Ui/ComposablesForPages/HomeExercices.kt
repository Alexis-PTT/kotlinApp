package com.mainProject.Ui.ComposablesForPages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mainProject.Db.DataClassGraphic.ExerciseRecordData
import com.mainProject.ViewModel.ViewModelMain

class HomeExercices {

    @Composable
    fun listOfExercices(
        viewModel: ViewModelMain,
        navController: NavController
    ){
        var exercises by remember { mutableStateOf<List<ExerciseRecordData>>(emptyList()) }
        LaunchedEffect(Unit) {
            exercises = viewModel.getExercises()
        }
        //TODO : IMPLEMENT add some parameters to chose by muscle or muscle group
        // if you chose a muscleGroup only the muscles in this group will appear in the muscle selection
        // all by default
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.padding(8.dp)){
                exercises.forEach{ exercise ->
                    item{
                        bubbleExercice(exercise,navController)
                    }
                }
            }
        }
    }


    @Composable
    fun bubbleExercice(exercise : ExerciseRecordData, navController :  NavController) {
        Card(
            modifier = Modifier.padding(8.dp),
            onClick = {
                navController.navigate("exerciseInfoPage/${exercise.exercice_id}")
            })
        {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("${exercise.name}")
                Text("groupes musculaires touchée :",softWrap = true)
                Text("${exercise.muscleGroup.joinToString(", ")}")
                Text("niveau actuel :", softWrap = true)
                Text("reps :${exercise.rep_count}")
                Text("set :${exercise.set_count}")
                Text("poids :${exercise.weight}")
            }
        }

    }
}