package com.mainProject.NotUsedForNow


import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.cartesianLayerPadding
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.component.shapeComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.insets
import com.patrykandpatrick.vico.compose.common.shape.rounded
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis.ItemPlacer
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis.ItemPlacer.Companion.step
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.decoration.HorizontalLine
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.Position
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shape.CorneredShape

class test23 {

    private val data =
        mapOf("14/07" to 2, "15/07" to 4, "16/07" to 3, "17/07" to 2, "18/07" to 1, "19/07" to 1, "20/07" to 1)


    private val BottomAxisLabelKey = ExtraStore.Key<List<String>>()

    private val BottomAxisValueFormatter = CartesianValueFormatter { context, x, _ ->
        context.model.extraStore[BottomAxisLabelKey][x.toInt()]
    }

    @Composable
    private fun JetpackComposeRockMetalRatios(
        modelProducer: CartesianChartModelProducer,
        modifier: Modifier = Modifier,
    ) {
        CartesianChartHost(
            chart =
                rememberCartesianChart(
                    rememberColumnCartesianLayer(
                        ColumnCartesianLayer.ColumnProvider.series(
                            rememberLineComponent(fill = fill(Color(0xffff5500)), thickness = 16.dp)
                        )
                    ),
                    startAxis = VerticalAxis.rememberStart( itemPlacer = step(
                        step = {1.0}
                    ) ),
                    bottomAxis =
                        HorizontalAxis.rememberBottom(
                            itemPlacer = remember { ItemPlacer.segmented() },
                            valueFormatter = BottomAxisValueFormatter,
                        ),
                    layerPadding = { cartesianLayerPadding(scalableStart = 8.dp, scalableEnd = 8.dp) },
                ),
            modelProducer = modelProducer,
            modifier = modifier.height(220.dp),
            scrollState = rememberVicoScrollState(scrollEnabled = false),
        )
    }


    @Composable
    fun JetpackComposeRockMetalRatios(modifier: Modifier = Modifier) {
        val modelProducer = remember { CartesianChartModelProducer() }
        LaunchedEffect(Unit) {
            modelProducer.runTransaction {
                columnSeries { series(data.values) }
                extras { it[BottomAxisLabelKey] = data.keys.toList() }
            }
        }
        JetpackComposeRockMetalRatios(modelProducer, modifier)
    }

}