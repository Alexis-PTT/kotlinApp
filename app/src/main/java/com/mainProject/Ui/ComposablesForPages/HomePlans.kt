package com.mainProject.Ui.ComposablesForPages

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mainProject.Db.DataClassGraphic.PlanHomeData
import com.mainProject.Db.DataClassSQL.Exercise
import com.mainProject.Db.DataClassSQL.SportPlan
import com.mainProject.ViewModel.ViewModelMain
import kotlinx.coroutines.launch


class HomePlans (){

    val colorText = Color(30, 52, 23, 255)

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
            bubbleAddPlan(viewModel)
            LazyColumn(modifier = Modifier.padding(8.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally){
                plans.forEach{ plan ->
                    item(){
                        bubblePlan(plan,navController)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun bubblePlan(plan : PlanHomeData, navController :  NavController) {
        Card(
            modifier = Modifier.padding(8.dp),
            onClick = {
                navController.navigate("exerciseInfoPage/${plan.plan_id}")

            }
        ){
            Box(Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(89, 103, 52, 255))// Hauteur du header
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        plan.name,
                        modifier = Modifier.padding(15.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                        color = Color.White
                    )
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "delete the exercice",
                        modifier = Modifier.padding(15.dp),
                        tint = Color.White
                    )
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                HorizontalDivider(thickness = 2.dp)
                Text(
                    "groupes musculaires touchée :",
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    color = colorText
                )
                Text("  " + plan.muscleGroups.joinToString(", "),
                    modifier = Modifier.padding(bottom = 10.dp),
                    color = colorText)
                Text("nombre d'exercices : ${plan.exercice_quantity}",
                    fontWeight = FontWeight.Bold,
                    color = colorText)
            }
        }
    }

    //bubble at the top of my list of plans to add a new plan to my list
    @SuppressLint("MutableCollectionMutableState")
    @Composable
    fun bubbleAddPlan(viewModel: ViewModelMain){


        var namePlan by remember { mutableStateOf("") }
        var descPlan by remember { mutableStateOf("") }
        var listExercisesLeft = remember { mutableStateListOf<Exercise>() }
        var listExercisesToAdd = remember { mutableStateListOf<Exercise>()}
        var stateOfAdding by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            listExercisesLeft.addAll(viewModel.getExercises());
        }
        LaunchedEffect(listExercisesToAdd) {
            Log.d("","changement")
        }


        Card(
            modifier = Modifier.padding(16.dp),
            onClick = {
            }
        ){

            Box(Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(89, 103, 52, 255))// Hauteur du header
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        "Créer un plan nouveau plan",
                        modifier = Modifier.padding(15.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                        color = Color.White
                    )
                    IconButton(
                        onClick ={
                            stateOfAdding=!stateOfAdding
                        }
                    ){

                        Icon(
                        imageVector = if (!stateOfAdding) Icons.Default.Add else Icons.Default.Clear,
                        contentDescription = "add a plan",
                        tint = Color.White)
                    }
                }
            }
            if (stateOfAdding){
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = namePlan,
                        singleLine = true,
                        onValueChange = { newName ->
                            namePlan=newName
                        },
                        label = {"nom séance"}
                    )
                    HorizontalDivider(thickness = 2.dp)
                    OutlinedTextField(
                        value = descPlan,
                        singleLine = true,
                        onValueChange = { newDesc ->
                            descPlan=newDesc
                        },
                        label = {"description séance"}
                    )

                    HorizontalDivider(thickness = 2.dp)
                    Text("Exercises :")
                    listExercisesToAdd.forEach { exercise ->
                        Text(exercise.name_exercise)
                    }

                    HorizontalDivider(thickness = 2.dp)
                    scrollableDropDowMenu(listExercisesToAdd,listExercisesLeft)

                    HorizontalDivider(thickness = 2.dp)
                    Button(onClick = {
                        //TODO : IMPLEMENT make this function
                        scope.launch {
                            viewModel.addSportPlan(listExercisesToAdd, SportPlan(name_plan = namePlan,desc_plan = descPlan))
                        }
                    }) {
                        Text("Créée")
                    }
                }
            }
        }
    }

    @Composable
    fun scrollableDropDowMenu(listExercicesToAdd : SnapshotStateList<Exercise>,listExercicesLeft : SnapshotStateList<Exercise>) {
        var expanded by remember { mutableStateOf(false) }


        Button(onClick = { expanded = true }) {
            Text("TADA")
        }
        // drop down menu where you can choose which exercises will by part of your planned musculation session
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded =!expanded },
            scrollState = rememberScrollState(),
            modifier = Modifier.heightIn(max = 250.dp) // Limit height to allow a scrolling part if there is too many items
        ) {
            //action executed on clic for each execises in the dropable list
            listExercicesLeft.forEach { exercise ->
                DropdownMenuItem(
                    text = { Text(exercise.name_exercise) },
                    onClick = {
                        Log.d("","${exercise.exercise_id}" )
                        listExercicesToAdd.add(exercise.copy())
                        listExercicesLeft.remove(exercise)
                        Log.d("","toAdd" )
                        listExercicesToAdd.forEach {exercise1 -> Log.d("","${exercise1.name_exercise}" )  }
                        Log.d("","left" )
                        listExercicesLeft.forEach {exercise2 -> Log.d("","${exercise2.name_exercise}" )  }
                    }
                )
            }
        }
        HorizontalDivider(thickness = 4.dp,
            modifier = Modifier.padding(10.dp)
        )
    }
}