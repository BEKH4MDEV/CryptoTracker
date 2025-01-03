package com.plcoding.cryptotracker.crypto.presentation.coin_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.cryptotracker.crypto.domain.CoinPrice
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.random.Random
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.IndicatorCount
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.PopupProperties
import kotlin.math.round

@Composable
fun LineChart(
    dataPoints: List<DataPoint>,
    style: ChartStyle,
    visibleDataPointsIndices: IntRange,
    unit: String,
    modifier: Modifier = Modifier
) {
    // Data to draw the chart

    val visibleDataPoints = remember(dataPoints, visibleDataPointsIndices) {
        dataPoints.slice(visibleDataPointsIndices)
    }

    val dataPointPrices = remember(visibleDataPoints) {
        visibleDataPoints.map {
            it.y.toDouble()
        }
    }

    val xLabels = remember(visibleDataPoints) {
        visibleDataPoints.map {
            it.xLabel
        }
    }

    val minValue = remember(dataPointPrices) {
        dataPointPrices.minOrNull() ?: 0.0
    }

    val maxValue = remember(dataPointPrices) {
        dataPointPrices.maxOrNull() ?: 0.0
    }

    LineChart(
        modifier = modifier
            .fillMaxSize(),
        data = listOf(
            Line(
                label = "",
                values = dataPointPrices,
                color = SolidColor(style.chartLineColor),
                curvedEdges = true,
                drawStyle = DrawStyle.Stroke(style.chartLineThickness),
                firstGradientFillColor = style.fillLineColor,
                secondGradientFillColor = Color.Transparent,
            )
        ),
        gridProperties = GridProperties(
            xAxisProperties = GridProperties.AxisProperties(
                thickness = style.gridThickness,
                lineCount = round((visibleDataPoints.size / 2f)).coerceAtLeast(3f).toInt(),
                color = SolidColor(
                    style.gridColor
                )
            ),
            yAxisProperties = GridProperties.AxisProperties(
                enabled = false,
                thickness = style.gridThickness,
                lineCount = visibleDataPoints.size,
                color = SolidColor(
                    style.gridColor
                )
            ),
        ),
        minValue = minValue,
        maxValue = maxValue,
        labelProperties = LabelProperties(
            enabled = true,
            labels = xLabels,
            textStyle = LocalTextStyle.current.copy(
                color = style.textColor,
                textAlign = TextAlign.Center,
                fontSize = style.labelFontSize
            )
        ),
        popupProperties = PopupProperties(
            contentBuilder = {
                ValueLabel(
                    value = it.toFloat(),
                    unit = unit
                ).formatted()
            },
            containerColor = style.popUpContainerColor,
            textStyle = LocalTextStyle.current.copy(
                color = style.popUpContentColor,
                fontSize = style.labelFontSize
            )
        ),
        labelHelperProperties = LabelHelperProperties(
            enabled = false
        ),
        indicatorProperties = HorizontalIndicatorProperties(
            textStyle = LocalTextStyle.current.copy(
                color = style.textColor,
                textAlign = TextAlign.Center,
                fontSize = style.labelFontSize
            ),
            contentBuilder = {
                ValueLabel(
                    value = it.toFloat(),
                    unit = unit
                ).formatted()
            },
            count = IndicatorCount.CountBased(round((visibleDataPoints.size / 2f)).coerceAtLeast(3f).toInt())
        )
    )
}

@Preview(widthDp = 400)
@Composable
private fun LineChartPreview() {
    CryptoTrackerTheme {
        val coinHistoryRandomized = remember {
            (1..20).map {
                CoinPrice(
                    priceUsd = Random.nextFloat() * 1000.0,
                    dateTime = ZonedDateTime.now().plusDays(5).plusHours(it.toLong())
                )
            }
        }

        val style = ChartStyle(
            chartLineColor = MaterialTheme.colorScheme.primary,
            chartLineThickness = 3.dp,
            popUpContainerColor = MaterialTheme.colorScheme.primary,
            popUpContentColor = MaterialTheme.colorScheme.onPrimary,
            textColor = MaterialTheme.colorScheme.onBackground,
            gridColor = MaterialTheme.colorScheme.secondary.copy(
                alpha = .3f
            ),
            gridThickness = 1.dp,
            fillLineColor = MaterialTheme.colorScheme.primary.copy(
                alpha = .3f
            ),
            labelFontSize = 14.sp
        )

        val dataPoints = remember {
            coinHistoryRandomized.map {
                DataPoint(
                    x = it.dateTime.hour.toFloat(),
                    y = it.priceUsd.toFloat(),
                    xLabel = DateTimeFormatter
                        .ofPattern("ha\nM/d", Locale.ENGLISH)
                        .format(it.dateTime)
                )
            }
        }

        LineChart(
            dataPoints = dataPoints,
            style = style,
            visibleDataPointsIndices = 0..6,
            unit = "$",
            modifier = Modifier
                .width(700.dp)
                .height(300.dp)
                .background(Color.White),
        )
    }
}