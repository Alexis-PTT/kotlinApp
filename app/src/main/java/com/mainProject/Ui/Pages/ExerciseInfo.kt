package com.mainProject.Ui.Pages


import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarDefaults.contentColor
import androidx.compose.material3.TabRowDefaults.contentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mainProject.Ui.ComposablesForPages.GraphLineXDateYInt
import com.mainProject.Ui.ComposablesForPages.GraphMultiLineXDateYInt
import com.mainProject.Ui.Pages.Home.Destination
import com.mainProject.ViewModel.ViewModelMain
import java.time.LocalDateTime


class ExerciseInfo {

    @OptIn(UnstableApi::class)
    @SuppressLint("NewApi")
    @Composable
    fun statExercise( id : String, viewModel : ViewModelMain, modifier : Modifier) {

        var dateRecord by remember { mutableStateOf<List<LocalDateTime>>(emptyList()) }
        var selectedIndexTemp by remember { mutableIntStateOf(0) }
        var selectedIndexData by remember { mutableIntStateOf(0) }
        var selectedIndexReductionType by remember { mutableIntStateOf(0) }
        val optionsTemp = listOf("Jours","Semaines", "Mois")
        val optionsData = listOf("Poids", "Reps/Sets","Progression")
        val optionsReductionType = listOf("Moyenne", "Max")
        val graphOneLine = GraphLineXDateYInt()
        val graphTwoLines = GraphMultiLineXDateYInt()

        LaunchedEffect(Unit) {

        }

        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = modifier.padding(8.dp).fillMaxWidth()) {

                item {
                    Text(text = "id :${id}")
                }
                item {
                    if (dateRecord.size > 0) {
                        Text(text = "id :${dateRecord.get(0)}")
                    }
                }
                item {
                    Column(modifier = Modifier.padding(4.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        SingleChoiceSegmentedButtonRow {
                            optionsData.forEachIndexed { index, label ->
                                SegmentedButton(
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = index,
                                        count = 3
                                    ),
                                    onClick = { selectedIndexData = index },
                                    selected = index == selectedIndexData,
                                    label = { Text(label) }
                                )
                            }
                        }
                    }
                }
                item {
                    Column(modifier = Modifier.padding(4.dp).fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        SingleChoiceSegmentedButtonRow {
                            optionsReductionType.forEachIndexed { index, label ->
                                SegmentedButton(
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = index,
                                        count = 2
                                    ),
                                    onClick = { selectedIndexReductionType = index },
                                    selected = index == selectedIndexReductionType,
                                    label = { Text(label) }
                                )
                            }
                        }
                    }
                }
                item {
                    Column(modifier = Modifier.padding(4.dp).fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        SingleChoiceSegmentedButtonRow {
                            optionsTemp.forEachIndexed { index, label ->
                                SegmentedButton(
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = index,
                                        count = 3
                                    ),
                                    onClick = { selectedIndexTemp = index },
                                    selected = index == selectedIndexTemp,
                                    label = { Text(label) }
                                )
                            }
                        }

                    }
                }
                item {
                    if(selectedIndexData!=1){
                        graphOneLine.lineChartMain(
                            viewModel,
                            optionsTemp[selectedIndexTemp],
                            id = id,
                            optionsData[selectedIndexData],
                            optionsReductionType[selectedIndexReductionType]
                        )
                    }else{
                        graphTwoLines.lineChartMain(
                            viewModel,
                            optionsTemp[selectedIndexTemp],
                            id = id,
                            optionsData[selectedIndexData],
                            optionsReductionType[selectedIndexReductionType]
                        )
                    }

                }
            }
        }
    }


    @OptIn(UnstableApi::class)
    @kotlin.OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun mainExercise(id : String, viewModel : ViewModelMain, navController : NavController){
        var selectedBool by remember { mutableStateOf(false) }

        Scaffold(
            modifier = Modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            "Centered Top App Bar",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigate("home")
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    actions = {
                        FloatingActionButton(
                            onClick = { Log.d("","delete exercise")  },//TODO : IMPLEMENT
                            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(Icons.Filled.Clear, "Localized description")
                        }
                        FloatingActionButton(
                            onClick = { Log.d("","modify exercise") },//TODO : IMPLEMENT
                            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(Icons.Filled.Settings, "Localized description")
                        }

                    }

                )
            }
        ){ contentPadding ->
            statExercise(id,viewModel, Modifier.padding(contentPadding))
        }
    }
}
