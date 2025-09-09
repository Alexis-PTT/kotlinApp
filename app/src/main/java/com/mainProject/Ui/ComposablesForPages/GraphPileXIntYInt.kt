package com.mainProject.Ui.ComposablesForPages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import com.mainProject.ViewModel.ViewModelMain
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.shapeComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.rememberHorizontalLegend
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.LegendItem
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import java.time.LocalDate
import java.time.Month

@UnstableApi
class GraphPileXIntYInt {


    @SuppressLint("NewApi")
    var currentDate : LocalDate = LocalDate.now()
    @SuppressLint("NewApi")
    val bottomAxisFormatterWeek = CartesianValueFormatter { context, xValue, _ ->
        val ThrusdayDate= if (currentDate.dayOfWeek.value>4)
            currentDate.minusDays(7-currentDate.dayOfWeek.value.toLong())
        else
            currentDate.plusDays(4-currentDate.dayOfWeek.value.toLong())
        val weekInDate = (ThrusdayDate.minusWeeks(((6-xValue).toLong())))
        var weekInInt = 1+((weekInDate).dayOfYear/7)
        if ((weekInInt == 1) || weekInDate.year!=weekInDate.plusWeeks(1).year)
                "${weekInDate.year}-" + "S${weekInInt}"
        else
            "S${weekInInt}"
    }

    @SuppressLint("NewApi")
    val bottomAxisFormatterDay = CartesianValueFormatter { context, xValue, _ ->
        val xDay = (currentDate.minusDays((6-xValue).toLong()))
        "${xDay.dayOfMonth} ${(xDay.month).name.substring(0, 3)}"

    }
    @SuppressLint("NewApi")
    val bottomAxisFormatterMonth = CartesianValueFormatter { context, xValue, _ ->
        val ThrusdayDate= if (currentDate.dayOfWeek.value>4)
            currentDate.minusDays(7-currentDate.dayOfWeek.value.toLong())
        else
            currentDate.plusDays(4-currentDate.dayOfWeek.value.toLong())
        val xMonth = ThrusdayDate.minusMonths((6-xValue).toLong())
        if((xMonth.month==Month.JANUARY) || xMonth.month==Month.DECEMBER){
           "${xMonth.month.name.substring(0, 3)}-${xMonth.year}"
        }else {
            xMonth.month.name.substring(0, 3)
        }
    }

    @Composable
    private fun JetpackComposeBasicLineChart(
        modelProducer: CartesianChartModelProducer,
        temporality : String
    ) {
        CartesianChartHost(
            chart =
                rememberCartesianChart(
                    rememberColumnCartesianLayer(
                        ColumnCartesianLayer.ColumnProvider.series(
                            rememberLineComponent(fill = fill(Color(0xFF3D8BEF)), thickness = 16.dp)
                        )
                    ),
                    startAxis = VerticalAxis.Companion.rememberStart(
                        itemPlacer = VerticalAxis.ItemPlacer.step({ 1.0 })
                    ),
                    bottomAxis = HorizontalAxis.Companion.rememberBottom(
                        label = TextComponent(
                            textSizeSp = 10F
                        ),
                        valueFormatter = if (temporality=="Mois") bottomAxisFormatterMonth else
                            if (temporality=="Semaines") bottomAxisFormatterWeek else bottomAxisFormatterDay,
                        itemPlacer = HorizontalAxis.ItemPlacer.aligned(spacing = {1})
                    ),
                    legend = rememberHorizontalLegend(items = {
                        add(
                            LegendItem(
                                shapeComponent(
                                    fill(Color(0xFF3D8BEF)),
                                    CorneredShape.Companion.Pill
                                ),
                                TextComponent(),
                                "test",
                            )
                        )
                    })
                ),
            modelProducer = modelProducer
        )
    }


    @SuppressLint("NewApi")
    @Composable
    fun SimpleGraphMain(temporality: String,viewModelMain: ViewModelMain,id : String){
        val modelProducer = remember { CartesianChartModelProducer() }
        LaunchedEffect(temporality) {
            val stat = viewModelMain.getDateRecordOfSessions(temporality)

            modelProducer.runTransaction {
                columnSeries { series(stat) }
            }
        }
        Box(
            modifier = Modifier.Companion
                .fillMaxSize()
        ) {
            JetpackComposeBasicLineChart(modelProducer,temporality)
        }
    }
}