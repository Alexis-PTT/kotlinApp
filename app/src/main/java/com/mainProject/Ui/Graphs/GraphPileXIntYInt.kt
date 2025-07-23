package com.mainProject.Ui.Graphs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.shapeComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.rememberHorizontalLegend
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.LegendItem
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import kotlinx.coroutines.runBlocking

class GraphPileXIntYInt {
    @Composable
    private fun JetpackComposeBasicLineChart(
        modelProducer: CartesianChartModelProducer,
        modifier: Modifier = Modifier.Companion,
    ) {
        CartesianChartHost(
            chart =
                rememberCartesianChart(
                    rememberColumnCartesianLayer(
                        ColumnCartesianLayer.ColumnProvider.series(
                            rememberLineComponent(fill = fill(Color(0xFF3D8BEF)), thickness = 16.dp)
                        )
                    ),
                    startAxis = VerticalAxis.Companion.rememberStart(),
                    bottomAxis = HorizontalAxis.Companion.rememberBottom(),
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
            modelProducer = modelProducer,
            modifier = modifier,
        )
    }


    @Composable
    fun SimpleGraphMain(valueSeries : IntArray){
        val modelProducer = remember { CartesianChartModelProducer() }
        runBlocking {
            modelProducer.runTransaction {
                columnSeries { series(valueSeries.toList()) }
            }
        }
        Box(
            modifier = Modifier.Companion
                .fillMaxSize()
        ) {
            JetpackComposeBasicLineChart(modelProducer)
        }
    }

}