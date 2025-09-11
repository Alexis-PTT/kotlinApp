package com.mainProject.Ui.ComposablesForPages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mainProject.Db.DataClassGraphic.ExerciseHomeData
import com.mainProject.Db.DataClassGraphic.PlanHomeData
import com.mainProject.ViewModel.ViewModelMain

class HomePlans (){

    @Composable
    fun listOfPlans(
        viewModel: ViewModelMain,
        navController: NavController
    ){
        var plans by remember { mutableStateOf<List<PlanHomeData>>(emptyList()) }
        LaunchedEffect(Unit) {
            plans = viewModel.getPlansForHome()

        }
        //TODO : IMPLEMENT add some parameters to chose by muscle or muscle group
        // if you chose a muscleGroup only the muscles in this group will appear in the muscle selection
        // all by default
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.padding(8.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally){
                plans.forEach{ plan ->
                    item(){
                        bubbleExercice(plan,navController)
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun bubbleExercice(plan : PlanHomeData, navController :  NavController) {
        Card(
            modifier = Modifier.padding(8.dp),
            onClick = {
                //TODO :
                //navController.navigate("exerciseInfoPage/${exercise.exercise_id}")
            })
        {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(  modifier = Modifier
                    .width(290.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,){
                    Text(
                        plan.name,
                        modifier = Modifier.padding(5.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left
                    )
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "delete the plan",
                        modifier = Modifier.padding(5.dp)
                    )


                }
                Text(
                    "groupes musculaires touchée :",
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
                Text("  " + plan.muscleGroups.joinToString(", "),
                    modifier = Modifier.padding(bottom = 10.dp))
                Text("  nombre d'exercices : ${plan.exercice_quantity}")
            }

        }



    }
}