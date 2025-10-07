package com.mainProject.Ui.Pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class WorkoutDataEntry {

    @Preview
    @Composable
    fun dataEntryMain()  {

        var nameSessionPlan by remember { mutableStateOf<String>("") }
        var listExo by remember { mutableStateOf<List<String>>(listOf("")) }
        var listReps by remember { mutableStateOf<List<Int>>(listOf(0)) }
        var listSets by remember { mutableStateOf<List<Int>>(listOf(0)) }
        var listWeight by remember { mutableStateOf<List<Float>>(listOf(0.0F)) }

        Column(
            modifier = Modifier.Companion.fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.Companion.padding(8.dp)) {
                item {
                    Box(
                        modifier = Modifier.Companion
                            .fillMaxSize(), // prend tout l'espace
                        contentAlignment = Alignment.Companion.Center // centre le contenu
                    ) {
                        OutlinedTextField(
                            value = nameSessionPlan,
                            singleLine = true,
                            onValueChange = { newName -> nameSessionPlan = newName },
                            label = { Text("nom seance", fontSize = 12.sp) },
                            modifier = Modifier.Companion.padding(5.dp).width(160.dp)
                        )
                    }
                }
                for (i in 0..listExo.size - 1) {
                    item {
                        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                            OutlinedTextField(
                                value = listExo.get(i),
                                singleLine = true,
                                onValueChange = { newExo ->
                                    val updatedList = listExo.toMutableList()
                                    updatedList[i] = newExo
                                    listExo = updatedList
                                },
                                label = { Text("nom exercice", fontSize = 12.sp) },
                                modifier = Modifier.Companion.padding(5.dp).width(160.dp)
                            )
                            OutlinedTextField(
                                value = if (listWeight.get(i) != 0.0f) "${listWeight.get(i)}" else "",
                                onValueChange = { newWeight ->
                                    val updatedList2 = listWeight.toMutableList()
                                    updatedList2[i] = newWeight.toFloatOrNull() ?: 0f
                                    listWeight = updatedList2
                                },
                                label = { Text("KG", fontSize = 12.sp) },
                                modifier = Modifier.Companion.padding(5.dp).width(54.dp)
                            )
                            OutlinedTextField(
                                value = "${listSets.get(i)}",
                                onValueChange = { newSet ->
                                    val updatedList = listSets.toMutableList()
                                    updatedList[i] = newSet.toIntOrNull() ?: 0
                                    listSets = updatedList
                                },
                                label = { Text("Set", fontSize = 12.sp) },
                                modifier = Modifier.Companion.padding(5.dp).width(54.dp)
                            )
                            OutlinedTextField(
                                value = "${listReps.get(i)}",
                                onValueChange = { newRep ->
                                    val updatedList = listReps.toMutableList()
                                    updatedList[i] = newRep.toIntOrNull() ?: 0
                                    listReps = updatedList
                                },
                                label = { Text("Rep", fontSize = 12.sp) },
                                modifier = Modifier.Companion.padding(5.dp).width(54.dp)
                            )
                        }
                    }
                }

                item {
                    Row() {
                        Button(onClick = {
                            val updatedExo = listExo.toMutableList()
                            val updatedRep = listReps.toMutableList()
                            val updatedSet = listSets.toMutableList()
                            val updatedWeight = listWeight.toMutableList()
                            listExo = updatedExo.plus("")
                            listReps = updatedRep.plus(0)
                            listSets = updatedSet.plus(0)
                            listWeight = updatedWeight.plus(0.0F)
                        }, modifier = Modifier.Companion.padding(4.dp)) {
                            Text("rien")
                        }

                        Button(onClick = {
                            val updatedExo = listExo.toMutableList()
                            val updatedRep = listReps.toMutableList()
                            val updatedSet = listSets.toMutableList()
                            val updatedWeight = listWeight.toMutableList()
                            listExo = updatedExo.plus("")
                            listReps = updatedRep.plus(0)
                            listSets = updatedSet.plus(0)
                            listWeight = updatedWeight.plus(0.0F)
                        }, modifier = Modifier.Companion.padding(4.dp)) {
                            Text("rien")
                        }
                    }
                }
            }
        }
    }

}