package com.mainProject.Ui.Pages

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mainProject.ViewModel.ViewModelMain
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.Line
import kotlin.collections.forEach

class Home {

    enum class Destination(
        val route: String,
        val label: String,
        val icon: ImageVector,
        val contentDescription: String
    ) {
        SONGS("", "", Icons.AutoMirrored.Filled.List, "Songs"),
        ALBUM("", "Nouvel enregistrement", Icons.Default.AddCircle, "Album"),
        PLAYLISTS("", "Plan séance", Icons.Default.DateRange, "Playlist")
    }
    @Composable
    fun mainPage(
        viewModel: ViewModelMain,
        navController: NavController
    ){
        /*
        //var sessions by remember { mutableStateOf<List<SportSession>>(emptyList()) }

        LaunchedEffect(Unit) {
            //sessions = viewModel.getAllSessions()
        }

        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.padding(8.dp)){

                /*sessions.forEach{ session ->
                    item{
                        sessionPlanBubble(session,navController)
                    }
                }*/
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

         */
        NavigationBar()


    }





    @Composable
    fun PlaylistScreen(modifier: Modifier = Modifier) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Playlist Screen")
        }
    }

    @Composable
    fun AppNavHost(
        navController: NavHostController,
        startDestination: Destination,
        modifier: Modifier = Modifier
    ) {
        NavHost(
            navController,
            startDestination = startDestination.route
        ) {
            Destination.entries.forEach { destination ->
                composable(destination.route) {
                    when (destination) {
                        Destination.SONGS -> SongsScreen()
                        Destination.ALBUM -> AlbumScreen()
                        Destination.PLAYLISTS -> PlaylistScreen()
                    }
                }
            }
        }
    }
    @Composable
    fun NavigationBar(modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        val startDestination = Destination.SONGS
        var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

        Scaffold(
            modifier = modifier,
            bottomBar = {
                NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                    Destination.entries.forEachIndexed { index, destination ->
                        NavigationBarItem(
                            selected = selectedDestination == index,
                            onClick = {
                                navController.navigate(route = destination.route)
                                selectedDestination = index
                            },
                            icon = {
                                Icon(
                                    destination.icon,
                                    contentDescription = destination.contentDescription
                                )
                            },
                            label = { Text(destination.label) }
                        )
                    }
                }
            }
        ) { contentPadding ->
            AppNavHost(navController, startDestination, modifier = Modifier.padding(contentPadding))
        }
    }



    /*"@Composable
    fun bubble(session : SportSession, navController :  NavController){
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
    }*/


}