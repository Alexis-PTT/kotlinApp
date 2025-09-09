package com.mainProject.Ui.Pages


import android.annotation.SuppressLint
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mainProject.Ui.ComposablesForPages.GraphLineXDateYInt
import com.mainProject.Ui.ComposablesForPages.GraphMultiLineXDateYInt
import com.mainProject.ViewModel.ViewModelMain
import java.time.LocalDateTime


class ExerciseInfo {

    @SuppressLint("NewApi")
    @Composable
    fun listExercice( id : String, viewModel : ViewModelMain) {

        var dateRecord by remember { mutableStateOf<List<LocalDateTime>>(emptyList()) }
        var selectedIndexTemp by remember { mutableIntStateOf(0) }
        var selectedIndexData by remember { mutableIntStateOf(0) }
        var selectedIndexReductionType by remember { mutableIntStateOf(0) }
        val optionsTemp = listOf("Jours","Semaines", "Mois")
        val optionsData = listOf("Poids", "Reps/Sets","Effort relatif")
        val optionsReductionType = listOf("Moyenne", "Max")
        val graphOneLine = GraphLineXDateYInt()
        val graphTwoLines = GraphMultiLineXDateYInt()

        LaunchedEffect(Unit) {

        }

        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.padding(8.dp)) {

                item {
                    Text(text = "id :${id}")
                }
                item {
                    if (dateRecord.size > 0) {
                        Text(text = "id :${dateRecord.get(0)}")
                    }
                }
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
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
                    Column(modifier = Modifier.padding(16.dp)) {
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
                    Column(modifier = Modifier.padding(16.dp)) {
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
                    if(selectedIndexData==1){
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
}
