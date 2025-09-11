package com.mainProject.Ui.Pages

import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import com.mainProject.Ui.ComposablesForPages.GraphPileXIntYInt
import com.mainProject.ViewModel.ViewModelMain

class SessionInfo {


    @OptIn(UnstableApi::class)
    @SuppressLint("NewApi")
    @Composable
    fun listExercice( id : String, viewModel : ViewModelMain,navController : NavController){

        //var exercices by remember { mutableStateOf<List<Exercice>>(emptyList()) }
        var selectedIndex by remember { mutableIntStateOf(0) }
        val options = listOf("Jours","Semaines", "Mois")
        val graph = GraphPileXIntYInt()



        LaunchedEffect(Unit) {
            //exercices = viewModel.getExerciceFromSession(id.toInt())
        }


        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.padding(8.dp)){

                item{
                    Text(text = "id :${id}")
                }

                item{
                    Column(modifier = Modifier.padding(16.dp)){
                        SingleChoiceSegmentedButtonRow {
                            options.forEachIndexed { index, label ->
                                SegmentedButton(
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = index,
                                        count = 3
                                    ),
                                    onClick = {
                                        selectedIndex = index
                                    },
                                    selected = index == selectedIndex,
                                    label = { Text(label) }
                                )
                            }
                        }
                        //graph.SimpleGraphMain(options[selectedIndex],viewModel,id)
                    }
                }
                /*
                exercices.forEach{ exercice ->
                    item{
                        exerciceBubble(exercice,navController)
                    }
                }*/
            }
        }
    }

    /*@Composable
    fun exerciceBubble(exercice: Exercice,navController : NavController){
        Card(modifier = Modifier.padding(8.dp),
            onClick = {
                navController.navigate("exerciseInfoPage/${exercice.id_ex}")
            })
        {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "exercice nom : ${exercice.name_ex}(${exercice.id_ex})")
                Text(text = "type : ${exercice.exercice_type}")
            }
        }
    }*/

}