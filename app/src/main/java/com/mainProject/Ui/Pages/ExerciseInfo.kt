package com.mainProject.Ui.Pages


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.sp
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


class ExerciseInfo {

    @SuppressLint("NewApi")
    @Composable
    fun listExercice( id : String, viewModel : ViewModelMain) {

        var dateRecord by remember { mutableStateOf<List<LocalDateTime>>(emptyList()) }
        var selectedIndex by remember { mutableIntStateOf(0) }
        val options = listOf("Semaines", "Mois", "Années")

        LaunchedEffect(Unit) {
            //dateRecord = viewModel.getDateRecordOfSession(id.toInt())
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
                            options.forEachIndexed { index, label ->
                                SegmentedButton(
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = index,
                                        count = 3
                                    ),
                                    onClick = { selectedIndex = index },
                                    selected = index == selectedIndex,
                                    label = { Text(label) }
                                )
                            }
                        }
                        SimpleGraphMain(options.get(selectedIndex))
                    }
                }
            }
        }
    }

    @Composable
    fun JetpackComposeBasicLineChart(
        modelProducer: CartesianChartModelProducer,
        modifier: Modifier = Modifier,
    ) {
        CartesianChartHost(
            chart =
                rememberCartesianChart(
                    rememberLineCartesianLayer(),
                    startAxis = VerticalAxis.rememberStart(),
                    bottomAxis = HorizontalAxis.rememberBottom(),
                ),
            modelProducer = modelProducer,
            modifier = modifier,
        )
    }

    @Composable
    fun SimpleGraphMain(state: String) {
        val modelProducer = remember { CartesianChartModelProducer() }
        runBlocking {
            modelProducer.runTransaction {
                lineSeries { series(7, 5, 7, 1, 0, 1, 2, 5) }
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize().padding(16.dp),) {
                JetpackComposeBasicLineChart(modelProducer)
            }
            Text(text = "${state}")
        }
    }
}
