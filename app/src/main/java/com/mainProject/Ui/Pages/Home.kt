package com.mainProject.Ui.Pages

import android.R.attr.onClick
import android.graphics.drawable.Icon
import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabIndicatorScope
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mainProject.Ui.ComposablesForPages.HomeExercices
import com.mainProject.Ui.ComposablesForPages.HomePage
import com.mainProject.Ui.ComposablesForPages.HomePlans
import com.mainProject.ViewModel.ViewModelMain
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.Line
import kotlin.collections.forEach

class Home(val viewModel: ViewModelMain, val navController: NavController) {
    val listOfExercises= HomeExercices()
    val listOfPlans = HomePlans()
    val listOfStats = HomePage()
    val colorText = Color(30, 52, 23, 255)
    enum class Destination(
        val route: String,
        val label: String,
        val icon: ImageVector,
        val contentDescription: String
    ) {
        EXERCISE("a", "Liste d'exercices", Icons.AutoMirrored.Filled.List, "Liste d'exercices"),
        HOME("b", "acceuil", Icons.Default.Home, "Acceuil"),
        PLAN("c", "Plans séances", Icons.Default.DateRange, "Plans séances")
    }

    @Composable
    fun ScreenEx(modifier: Modifier) {
        Box(
            modifier = modifier.fillMaxSize(),
        ) {
            listOfExercises.listOfExercices(viewModel,navController)
        }
    }

    @Composable
    fun ScreenPlan(modifier: Modifier) {
        Box(
            modifier = modifier.fillMaxSize(),
        ) {
            listOfPlans.listOfPlans(viewModel,navController)
        }
    }
    @Composable
    fun ScreenHome(modifier: Modifier) {
        Box(
            modifier = modifier.fillMaxSize(),
        ) {
            listOfStats.homeGraphs(viewModel)
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
                        Destination.EXERCISE -> ScreenEx(modifier)
                        Destination.HOME -> ScreenHome(modifier)
                        Destination.PLAN -> ScreenPlan(modifier)
                    }
                }
            }
        }
    }

    @androidx.annotation.OptIn(UnstableApi::class)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun mainPage(modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        val startDestination = Destination.EXERCISE
        var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }


        Scaffold(
            modifier = modifier,
            topBar= {PrimaryTabRow(
                selectedTabIndex = selectedDestination,
                modifier = Modifier.padding(top = 15.dp),
                indicator = { TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(selectedDestination),
                        color = colorText // Mets ici la couleur que tu souhaites
                    )
                }
            ) {
                    Destination.entries.forEachIndexed { index, destination ->
                        Tab(
                            selectedContentColor = colorText,
                            unselectedContentColor = colorText,
                            selected = selectedDestination == index,
                            onClick = {
                                navController.navigate(route = destination.route)
                                selectedDestination = index
                            },
                            text = {
                                Text(
                                    color = colorText,
                                    text = destination.label,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            icon = {
                                Icon(
                                    destination.icon,
                                    contentDescription = destination.contentDescription,
                                    tint = colorText
                                )
                            }
                        )
                    }
                }
            }
        ){ contentPadding ->
            AppNavHost(navController, startDestination, modifier = Modifier.padding(contentPadding))
        }
        Box(
            modifier = Modifier
                .fillMaxSize() // Take the whole screen
                .padding(16.dp) // Optional padding from edges
        ) {
            LargeFloatingActionButton(
                onClick = { },//TODO : implement
                shape = CircleShape,
                modifier = Modifier.size(75.dp).align( Alignment.BottomEnd ),
                containerColor = colorText
            ) {
                Icon(Icons.Filled.Add, "Large floating action button",
                    modifier = Modifier.size(50.dp),
                    tint = Color.White)
            }
        }
    }
}