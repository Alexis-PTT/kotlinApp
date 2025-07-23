package com.mainProject.Ui.Pages

import android.R.attr.onClick
import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import com.mainProject.Db.DataClassSQL.Exercice
import com.mainProject.Ui.Graphs.GraphPileXIntYInt
import com.mainProject.ViewModel.ViewModelMain
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import kotlin.collections.forEach

class SessionInfo {


    @OptIn(UnstableApi::class)
    @SuppressLint("NewApi")
    @Composable
    fun listExercice( id : String, viewModel : ViewModelMain,navController : NavController){

        var exercices by remember { mutableStateOf<List<Exercice>>(emptyList()) }
        var dateRecord by remember { mutableStateOf<Array<IntArray>>(arrayOf(
            intArrayOf(0,0,0,0,0,0,0),
            intArrayOf(0,0,0,0,0,0,0),
            intArrayOf(0,0,0,0,0,0,0))) }
        var isLoading by remember { mutableStateOf(true) }
        var selectedIndex by remember { mutableIntStateOf(0) }
        val options = listOf("Jours","Semaines", "Mois")
        val graph = GraphPileXIntYInt()



        LaunchedEffect(Unit) {
            exercices = viewModel.getExerciceFromSession(id.toInt())
            for(y in 0..2){
                val stat = viewModel.getDateRecordOfSession(id.toInt(),options.get(y))
                for(i in 1..7){
                    if(stat.containsKey(i)) {
                        dateRecord[y][i-1] = stat.get(i)?.toInt()!!
                    }
                }
            }
            isLoading = false


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
                        if (isLoading) {
                            CircularProgressIndicator()
                        } else {
                            graph.SimpleGraphMain(dateRecord[selectedIndex])
                        }
                    }
                }
                exercices.forEach{ exercice ->
                    item{
                        exerciceBubble(exercice,navController)
                    }
                }
            }
        }
    }

    @Composable
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
    }

}