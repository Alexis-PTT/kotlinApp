/*
 * Copyright 2025 by Patryk Goworowski and Patrick Michalik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.patrykandpatrick.vico.sample.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.decoration.HorizontalLine
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.common.LegendItem
import com.patrykandpatrick.vico.core.common.Position
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import com.patrykandpatrick.vico.compose.cartesian.axis.*
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import kotlinx.coroutines.runBlocking


private val LegendLabelKey = ExtraStore.Key<Set<String>>()

@Composable
private fun rememberHorizontalLine(): HorizontalLine {
    val fill = fill(Color(0xfffdc8c4))
    val line = rememberLineComponent(fill = fill, thickness = 2.dp)
    val labelComponent =
        rememberTextComponent(
            margins = insets(start = 6.dp),
            padding = insets(start = 8.dp, end = 8.dp, bottom = 2.dp),
            background =
                shapeComponent(fill, CorneredShape.rounded(bottomLeft = 4.dp, bottomRight = 4.dp)),
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
    modifier: Modifier = Modifier,
) {
    val lineColors = listOf(Color(0xff916cda), Color(0xffd877d8), Color(0xfff094bb))
    val legendItemLabelComponent = rememberTextComponent(vicoTheme.textColor)
    val dayOfWeek = listOf<String>("lundi","mardi","mercredi","jeudi","vendredi","samedi","dimanche")

    val labels = listOf("Lundi", "Mardi", "Mercredi", "Jeudi")



    CartesianChartHost(
        rememberCartesianChart(
            rememberLineCartesianLayer(
                LineCartesianLayer.LineProvider.series(
                    lineColors.map { color ->
                        LineCartesianLayer.rememberLine(
                            fill = LineCartesianLayer.LineFill.single(fill(color)),
                            areaFill = null,
                            pointProvider =
                                LineCartesianLayer.PointProvider.single(
                                    LineCartesianLayer.point(rememberShapeComponent(fill(color), CorneredShape.Pill))
                                ),
                        )
                    }
                )
            ),
            startAxis = VerticalAxis.rememberStart(),
            bottomAxis = HorizontalAxis.rememberBottom(valueFormatter = CartesianValueFormatter.Default ),
            legend =
                rememberVerticalLegend(
                    items = { extraStore ->
                        extraStore[LegendLabelKey].forEachIndexed { index, label ->
                            add(
                                LegendItem(
                                    shapeComponent(fill(lineColors[index]), CorneredShape.Pill),
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

private val data =
    mapOf<String, Map<Int, Number>>(
        "Image recognition" to
                mapOf(
                    1 to -100,
                    2 to -44.16,
                    3 to -6.8,
                    4 to 0.69,
                    5 to 6.62,
                    6 to 11.69,
                    7 to 9.52,
                )
        //"Nuanced-language interpretation" to mapOf(),
        //"Programming" to mapOf(),
    )

@Composable
@Preview
private fun Preview() {
    val modelProducer = remember { CartesianChartModelProducer() }
    // Use `runBlocking` only for previews, which don’t support asynchronous execution.
    runBlocking {
        modelProducer.runTransaction {
            lineSeries { data.forEach { (_, map) -> series(map.keys, map.values) } }
            extras { extraStore -> extraStore[LegendLabelKey] = data.keys }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {JetpackComposeAITestScores(modelProducer) }
}