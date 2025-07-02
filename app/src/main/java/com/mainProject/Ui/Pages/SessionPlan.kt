package com.mainProject.Ui.Pages

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
import com.mainProject.Db.DataClassSQL.Exercice
import com.mainProject.ViewModel.ViewModelMain
import kotlin.collections.forEach

class SessionPlan {

    @Composable
    fun listExercice( id : String, viewModel : ViewModelMain){

        var exercices by remember { mutableStateOf<List<Exercice>>(emptyList()) }

        LaunchedEffect(Unit) {
            exercices = viewModel.getExerciceFromSession(id.toInt())
        }


        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            LazyColumn(
                modifier = Modifier.padding(8.dp)
            ){
                item{
                    Text(text = "id :${id}")
                }
                exercices.forEach{ exercice ->
                    item{
                        exerciceBubble(exercice)
                    }
                }
            }
        }
    }

    @Composable
    fun exerciceBubble(exercice: Exercice){
        Card(modifier = Modifier.padding(8.dp))
        {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "exercice nom : ${exercice.name_ex}(${exercice.id_ex})")
                Text(text = "type : ${exercice.exercice_type}")
            }
        }
    }

}