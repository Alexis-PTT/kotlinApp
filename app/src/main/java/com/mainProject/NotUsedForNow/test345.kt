package com.mainProject.NotUsedForNow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberCandlestickCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.candlestickSeries
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import kotlin.math.ceil
import kotlin.math.floor
import kotlinx.coroutines.runBlocking
import java.util.Calendar

class test345 {

    private val Y_STEP = 1.0


    private val StartAxisValueFormatter = CartesianValueFormatter.decimal(DecimalFormat("$#,###"))

    private val StartAxisItemPlacer = VerticalAxis.ItemPlacer.step({ Y_STEP })


    private val baseDate = Calendar.getInstance().apply {
        set(2025, Calendar.JULY, 1, 0, 0, 0)
        timeZone = TimeZone.getTimeZone("GMT")
    }

    private val BottomAxisValueFormatter =
        object : CartesianValueFormatter {
            private val dateFormat = SimpleDateFormat("dd MMM", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("GMT")
            }

            override fun format(
                context: CartesianMeasuringContext,
                value: Double,
                verticalAxisPosition: Axis.Position.Vertical?,
            ): String {
                val calendar = baseDate.clone() as Calendar
                calendar.add(Calendar.DATE, value.toInt() - 1) // valeur 1 = 1er juillet
                return dateFormat.format(calendar.time)
            }
        }

    @Composable
    private fun JetpackComposeGoldPrices(
        modelProducer: CartesianChartModelProducer,
        modifier: Modifier = Modifier,
    ) {
        CartesianChartHost(
            rememberCartesianChart(
                rememberCandlestickCartesianLayer(),
                startAxis =
                    VerticalAxis.rememberStart(
                        valueFormatter = StartAxisValueFormatter,
                        itemPlacer = StartAxisItemPlacer,
                    ),
                bottomAxis =
                    HorizontalAxis.rememberBottom(guideline = null, valueFormatter = BottomAxisValueFormatter)
            ),
            modelProducer,
            modifier.height(220.dp),
        )
    }

    private val x = (1..10).toList().reversed()




    private val opening =
        listOf<Number>(
            2634.899902,
            2635.300049,
            2630.899902,
            2628.800049,
            2623.600098,
            2624.600098,
            2623.100098,
            2629.399902,
            2635.100098,
            2618.100098,
            2623.699951,
            2613.699951,
            2612.199951,
            2618.699951,
            2619.100098,
            2620.300049,
            2621.800049,
            2620
        )

    private val closing =
        listOf<Number>(
            2635.399902,
            2631.199951,
            2628.899902,
            2623.600098,
            2624.899902,
            2623.100098,
            2629.5,
            2635.100098,
            2618.300049,
            2623.699951,
            2613.600098,
            2612,
            2618.399902,
            2619,
            2620.300049,
            2621.899902,
            2620,
            2620.199951,
            2620.899902
        )

    private val low =
        listOf<Number>(
            2632,
            2630.199951,
            2627.600098,
            2621.5,
            2623.199951,
            2623.100098,
            2621.300049,
            2628.600098,
            2618,
            2616.800049,
            2611.899902,
            2608.399902,
            2612.199951,
            2616.300049,
            2616.5,
            2619.699951,
            2619.699951,
            2617.800049,
            2618.600098
        )

    private val high =
        listOf<Number>(
            2636.5,
            2636.5,
            2631.899902,
            2629.600098,
            2629.699951,
            2626.899902,
            2631.699951,
            2636.199951,
            2636.899902,
            2626.800049,
            2623.899902,
            2615.699951,
            2618.899902,
            2619.699951,
            2621.699951,
            2623.199951,
            2622.100098,
            2620.899902,
            2621.800049
        )

    /*fun JetpackComposeGoldPrices(modifier: Modifier = Modifier) {
        val modelProducer = remember { CartesianChartModelProducer() }
        LaunchedEffect(Unit) {
            modelProducer.runTransaction {
                // Learn more: https://patrykandpatrick.com/y3c4gz.
                candlestickSeries(x, opening, closing, low, high)
            }
        }
        JetpackComposeGoldPrices(modelProducer, modifier)
    }*/

    @Composable
    @Preview
    private fun Preview() {
        val modelProducer = remember { CartesianChartModelProducer() }
        runBlocking {
            modelProducer.runTransaction {
                candlestickSeries(x, opening, closing, low, high)
            }
        }
        Box { JetpackComposeGoldPrices(modelProducer) }
    }
}