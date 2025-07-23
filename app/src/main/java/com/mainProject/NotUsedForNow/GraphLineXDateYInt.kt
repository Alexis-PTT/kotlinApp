package com.mainProject.NotUsedForNow

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mainProject.ViewModel.ViewModelMain
import com.patrykandpatrick.vico.multiplatform.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.HorizontalAxis.Companion.rememberBottom
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.multiplatform.cartesian.data.lineSeries
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.multiplatform.common.Fill
import com.patrykandpatrick.vico.multiplatform.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.multiplatform.common.shape.CorneredShape

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class GraphLineXDateYInt {

    @SuppressLint("NewApi")
    private val data =
        mapOf<LocalDate, Float>(
            LocalDate.parse("2022-07-01") to 2f,
            LocalDate.parse("2022-07-02") to 6f,
            LocalDate.parse("2022-07-04") to 4f,)

    private val StartAxisItemPlacer = VerticalAxis.ItemPlacer.step({ 1.0 })

    @SuppressLint("NewApi")
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM", Locale.FRANCE)


    @SuppressLint("NewApi")
    val bottomAxisValueFormatter = CartesianValueFormatter { context, xValue, _ ->
        val date = LocalDate.ofEpochDay(xValue.toLong())
        date.format(dateFormatter)
    }



    @SuppressLint("NewApi")
    @Composable
    fun ComposeMultiplatformAITestScores(modifier: Modifier = Modifier, viewModelMain : ViewModelMain) {
        val modelProducer = remember { CartesianChartModelProducer() }
        LaunchedEffect(Unit) {
            val xToDates = data.keys.associateBy { it.toEpochDay().toLong() }
            modelProducer.runTransaction {
                lineSeries { series(xToDates.keys, data.values) }
            }


            /*modelProducer.runTransaction {
                lineSeries {series(data.keys, data.values) }
            }*/
        }
        val lineColors = listOf(Color(0xff916cda))
        CartesianChartHost(
            rememberCartesianChart(
                rememberLineCartesianLayer(
                    LineCartesianLayer.LineProvider.series(
                        lineColors.map { color ->
                            LineCartesianLayer.rememberLine(
                                fill = LineCartesianLayer.LineFill.single(Fill(color)),
                                areaFill = null,
                                pointProvider =
                                    LineCartesianLayer.PointProvider.single(
                                        LineCartesianLayer.Point(rememberShapeComponent(Fill(color), CorneredShape.Pill))
                                    ),
                            )
                        }
                    )
                ),
                startAxis = VerticalAxis.rememberStart(itemPlacer = StartAxisItemPlacer),
                bottomAxis = rememberBottom(valueFormatter = bottomAxisValueFormatter),
            ),
            modelProducer,
            modifier.height(294.dp),
            rememberVicoScrollState(scrollEnabled = false),
        )
    }


}