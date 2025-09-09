package com.mainProject.Ui.ComposablesForPages

import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import com.mainProject.ViewModel.ViewModelMain
import com.patrykandpatrick.vico.multiplatform.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.multiplatform.cartesian.data.lineSeries
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.multiplatform.common.Fill
import com.patrykandpatrick.vico.multiplatform.common.Insets
import com.patrykandpatrick.vico.multiplatform.common.LegendItem
import com.patrykandpatrick.vico.multiplatform.common.component.*
import com.patrykandpatrick.vico.multiplatform.common.rememberHorizontalLegend
import com.patrykandpatrick.vico.multiplatform.common.shape.CorneredShape
import com.patrykandpatrick.vico.multiplatform.common.fill

import java.time.LocalDate
import java.time.Month

class GraphMultiLineXDateYInt {


    private val StartAxisItemPlacer = VerticalAxis.ItemPlacer.step({ 1.0 })

    @SuppressLint("NewApi")
    private var currentDate : LocalDate = LocalDate.now()

    private var dataChart  : MutableMap<String, Map<Int, Float>> = mutableMapOf("data1" to mapOf(), "data2" to mapOf())

    @SuppressLint("NewApi")
    private val bottomAxisFormatterWeek =
        CartesianValueFormatter { context, xValue, _ ->
            val ThrusdayDate = if (currentDate.dayOfWeek.value > 4)
                currentDate.minusDays(7 - currentDate.dayOfWeek.value.toLong())
            else
                currentDate.plusDays(4 - currentDate.dayOfWeek.value.toLong())
            val weekInDate = (ThrusdayDate.minusWeeks(((6 - xValue).toLong())))
            var weekInInt = 1 + ((weekInDate).dayOfYear / 7)
            if ((weekInInt == 1) || weekInDate.year != weekInDate.plusWeeks(1).year)
                "${weekInDate.year}-" + "S${weekInInt}"
            else
                "S${weekInInt}"
        }

    @SuppressLint("NewApi")
    private val bottomAxisFormatterDay =
        CartesianValueFormatter { context, xValue, _ ->
            val xDay = (currentDate.minusDays((6 - xValue).toLong()))
            "${xDay.dayOfMonth} ${(xDay.month).name.substring(0, 3)}"

        }
    @SuppressLint("NewApi")
    private val bottomAxisFormatterMonth =
        CartesianValueFormatter { context, xValue, _ ->
            val ThrusdayDate = if (currentDate.dayOfWeek.value > 4)
                currentDate.minusDays(7 - currentDate.dayOfWeek.value.toLong())
            else
                currentDate.plusDays(4 - currentDate.dayOfWeek.value.toLong())
            val xMonth = ThrusdayDate.minusMonths((6 - xValue).toLong())
            if ((xMonth.month == Month.JANUARY) || xMonth.month == Month.DECEMBER) {
                "${xMonth.month.name.substring(0, 3)}-${xMonth.year}"
            } else {
                xMonth.month.name.substring(0, 3)
            }
        }

    @Composable
    private fun lineChart(
        modelProducer: CartesianChartModelProducer,
        temporality : String,
        data : List<String>
    ){
        val lineColors = listOf(Color(0xFF2D4CFF), Color(0xFFE91E1E))
        CartesianChartHost(
            chart =
                rememberCartesianChart(
                    rememberLineCartesianLayer(
                        LineCartesianLayer.LineProvider.series(
                            lineColors.map { color ->
                                LineCartesianLayer.Companion.rememberLine(
                                    fill = LineCartesianLayer.LineFill.single(Fill(color)),
                                    areaFill = null,
                                    pointProvider =
                                        LineCartesianLayer.PointProvider.single(
                                            LineCartesianLayer.Point(
                                                rememberShapeComponent(
                                                    Fill(color),
                                                    CorneredShape.Companion.Pill
                                                )
                                            )
                                        ),
                                )
                            }
                        )
                    ),
                    startAxis = VerticalAxis.Companion.rememberStart(itemPlacer = StartAxisItemPlacer),
                    bottomAxis = HorizontalAxis.Companion.rememberBottom(
                        valueFormatter = if (temporality == "Mois") bottomAxisFormatterMonth else
                            if (temporality == "Semaines") bottomAxisFormatterWeek else bottomAxisFormatterDay,
                        itemPlacer = HorizontalAxis.ItemPlacer.aligned(spacing = { 1 })
                    ),
                    legend = rememberHorizontalLegend(items = {
                        add(
                            LegendItem(
                                ShapeComponent(
                                    fill(lineColors.get(0)),
                                    CorneredShape.Pill
                                ),
                                TextComponent(),
                                data.get(0),
                            )
                        )
                        add(
                            LegendItem(
                                ShapeComponent(
                                    fill(lineColors.get(1)),
                                    CorneredShape.Pill
                                ),
                                TextComponent(),
                                data.get(1),
                            )
                        )
                    }, padding = Insets(all = 10.dp))
                ),
                modelProducer,
                Modifier.Companion.height(294.dp),
                rememberVicoScrollState(scrollEnabled = false),
        )
    }

    @OptIn(UnstableApi::class)
    @SuppressLint("NewApi")
    @Composable
    fun lineChartMain(viewModelMain: ViewModelMain,temporality: String,id : String,dataNeeded : String,reductionType: String) {
        val modelProducer = remember { CartesianChartModelProducer() }
        LaunchedEffect(temporality,dataNeeded,reductionType) {
            dataChart["data1"]= viewModelMain.getRecordForExercice(id.toInt(),temporality,reductionType,"Set")
            dataChart["data2"]=viewModelMain.getRecordForExercice(id.toInt(),temporality,reductionType,"Rep")
            modelProducer.runTransaction {
                lineSeries { dataChart.forEach { (_,d)-> series(d.keys, d.values)} }
            }
        }
        Box{
            lineChart(modelProducer,temporality,dataChart.keys.toList())
        }
    }


}