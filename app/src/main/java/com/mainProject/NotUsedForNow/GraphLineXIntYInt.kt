package com.mainProject.NotUsedForNow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

class GraphLineXIntYInt {

    @Composable
    private fun JetpackComposeBasicLineChart(
        modelProducer: CartesianChartModelProducer,
        modifier: Modifier = Modifier.Companion,
    ) {
        CartesianChartHost(
            chart =
                rememberCartesianChart(
                    rememberLineCartesianLayer(),
                    startAxis = VerticalAxis.Companion.rememberStart(),
                    bottomAxis = HorizontalAxis.Companion.rememberBottom(),
                ),
            modelProducer = modelProducer,
            modifier = modifier,
        )
    }

    @Preview(showBackground = true)
    @Composable
    private fun SimpleGraphMain(){
        val modelProducer = remember { CartesianChartModelProducer() }
        runBlocking {
            modelProducer.runTransaction {
                lineSeries { series(7, 5, 7, 1, 0, 1, 2, 5) }
            }
        }
        Box(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(16.dp),
        ) {
            JetpackComposeBasicLineChart(modelProducer)
        }
    }

}