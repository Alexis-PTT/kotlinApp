package com.mainProject.Ui.Pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mainProject.Db.DataClassSQL.SportSession
import com.mainProject.ViewModel.ViewModelMain
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.Line
import kotlin.collections.forEach

class Home {

    @Composable
    fun mainPage(
        viewModel: ViewModelMain,
        navController: NavController
    ){
        var sessions by remember { mutableStateOf<List<SportSession>>(emptyList()) }

        LaunchedEffect(Unit) {
            sessions = viewModel.getAllSessions()
        }

        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.padding(8.dp)){

                sessions.forEach{ session ->
                    item{
                        sessionPlanBubble(session,navController)
                    }
                }
                item {
                    buttonRecordWorkout(navController)
                    Button(
                        onClick = { navController.navigate("workoutDataEntry") }
                    ){
                        Text("test")
                    }
                }

            }
        }

    }


    @Composable
    fun buttonRecordWorkout(navController : NavController){
        Button(onClick = {
            navController.navigate("rien2")
        }) {
            Text(text="+",
                fontSize = 32.sp)
        }
    }



    @Composable
    fun sessionPlanBubble(session : SportSession, navController :  NavController){
        Card(modifier = Modifier.padding(8.dp),
            onClick = {
                navController.navigate("sessionInfoPage/${session.session_id}")
            })
        {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Seance id : ${session.session_id}")
                Text(text = "crée depuis : ${session.name_session}")
            }
        }
    }


}