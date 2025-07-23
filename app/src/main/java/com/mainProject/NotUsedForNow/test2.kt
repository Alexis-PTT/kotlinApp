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
import com.patrykandpatrick.vico.compose.cartesian.layer.point
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.component.shapeComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.insets
import com.patrykandpatrick.vico.compose.common.rememberVerticalLegend
import com.patrykandpatrick.vico.compose.common.shape.rounded
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.decoration.HorizontalLine
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.common.LegendItem
import com.patrykandpatrick.vico.core.common.Position
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shape.CorneredShape

class test2() {

    private val data =
        mapOf("Ag" to 22378, "Mo" to 4478, "U" to 3624, "Sn" to 2231, "Li" to 1634, "W" to 1081)


    private val LegendLabelKey = ExtraStore.Key<List<String>>()

    private val BottomAxisValueFormatter = CartesianValueFormatter { context, x, _ ->
        context.model.extraStore[LegendLabelKey][x.toInt()]
    }


    @Composable
    private fun rememberHorizontalLine(): HorizontalLine {
        val fill = fill(Color(0xfffdc8c4))
        val line = rememberLineComponent(fill = fill, thickness = 2.dp)
        val labelComponent =
            rememberTextComponent(
                margins = insets(start = 6.dp),
                padding = insets(start = 8.dp, end = 8.dp, bottom = 2.dp),
                background =
                    shapeComponent(
                        fill,
                        CorneredShape.Companion.rounded(bottomLeft = 4.dp, bottomRight = 4.dp)
                    ),
            )
        return remember {
            HorizontalLine(
                y = { 0.0 },
                line = line,
                labelComponent = labelComponent,
                label = { "Human score" },
                verticalLabelPosition = Position.Vertical.Bottom,
            )
        }
    }

    @Composable
    private fun JetpackComposeAITestScores(
        modelProducer: CartesianChartModelProducer,
        modifier: Modifier = Modifier.Companion,
    ) {
        val lineColors = listOf(Color(0xff916cda), Color(0xffd877d8), Color(0xfff094bb))
        val legendItemLabelComponent = rememberTextComponent(vicoTheme.textColor)

        CartesianChartHost(
            rememberCartesianChart(
                rememberLineCartesianLayer(
                    LineCartesianLayer.LineProvider.series(
                        lineColors.map { color ->
                            LineCartesianLayer.Companion.rememberLine(
                                fill = LineCartesianLayer.LineFill.single(fill(color)),
                                areaFill = null,
                                pointProvider =
                                    LineCartesianLayer.PointProvider.single(
                                        LineCartesianLayer.Companion.point(
                                            rememberShapeComponent(
                                                fill(color),
                                                CorneredShape.Companion.Pill
                                            )
                                        )
                                    ),
                            )
                        }
                    )
                ),
                startAxis = VerticalAxis.Companion.rememberStart(),
                bottomAxis = HorizontalAxis.Companion.rememberBottom(
                    valueFormatter = BottomAxisValueFormatter
                ),
                legend =
                    rememberVerticalLegend(
                        items = { extraStore ->
                            extraStore[LegendLabelKey].forEachIndexed { index, label ->
                                add(
                                    LegendItem(
                                        shapeComponent(
                                            fill(lineColors[index]),
                                            CorneredShape.Companion.Pill
                                        ),
                                        legendItemLabelComponent,
                                        label,
                                    )
                                )
                            }
                        },
                        padding = insets(top = 16.dp),
                    ),
                decorations = listOf(rememberHorizontalLine()),
            ),
            modelProducer,
            modifier.height(300.dp),
            rememberVicoScrollState(scrollEnabled = false),
        )
    }

    @Preview
    @Composable
    fun Preview() {
        val modelProducer = remember { CartesianChartModelProducer() }
        LaunchedEffect(Unit) {
            modelProducer.runTransaction {
                columnSeries { series(data.values) }
                extras { it[LegendLabelKey] = data.keys.toList() }
            }
        }
        JetpackComposeAITestScores(modelProducer)
    }
}