package com.plcoding.cryptotracker.crypto.presentation.coin_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.plcoding.cryptotracker.R
import com.plcoding.cryptotracker.crypto.presentation.coin_detail.components.InfoCard
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListState
import com.plcoding.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.plcoding.cryptotracker.crypto.presentation.models.toDisplayableNumber
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import com.plcoding.cryptotracker.ui.theme.greenBackground

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoinDetailScreen(
    state: CoinListState,
    modifier: Modifier = Modifier,
    chartStyle: ChartStyle = ChartStyle(
        chartLineColor = MaterialTheme.colorScheme.primary,
        chartLineThickness = 3.dp,
        popUpContainerColor = MaterialTheme.colorScheme.primary,
        popUpContentColor = MaterialTheme.colorScheme.onPrimary,
        textColor = MaterialTheme.colorScheme.secondary.copy(
            alpha = .6f
        ),
        gridColor = MaterialTheme.colorScheme.secondary.copy(
            alpha = .3f
        ),
        gridThickness = .4.dp,
        fillLineColor = MaterialTheme.colorScheme.primary.copy(
            alpha = .3f
        ),
        labelFontSize = 14.sp,
        unit = "$"
    )
) {
    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.selectedCoin != null) {
        val coin = state.selectedCoin
        var columnWidthPx by remember { mutableIntStateOf(0) }
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .onGloballyPositioned {
                    columnWidthPx = it.size.width
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = coin.iconRes),
                contentDescription = coin.name,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = coin.name,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 45.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = coin.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                InfoCard(
                    title = stringResource(id = R.string.market_cap),
                    formattedText = "$ ${coin.marketCapUsd.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.stock)
                )

                InfoCard(
                    title = stringResource(id = R.string.price),
                    formattedText = "$ ${coin.priceUsd.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.dollar)
                )

                if (coin.changePercent24H != null) {
                    val absoluteChangeFormatted =
                        (coin.priceUsd.value * (coin.changePercent24H.value / 100))
                            .toDisplayableNumber()

                    val isPositive = coin.changePercent24H.value > 0.0

                    val contentColor = if (isPositive) {
                        if (isSystemInDarkTheme()) {
                            Color.Green
                        } else {
                            greenBackground
                        }
                    } else {
                        MaterialTheme.colorScheme.error
                    }

                    InfoCard(
                        title = stringResource(R.string.change_last_24h),
                        formattedText = absoluteChangeFormatted.formatted,
                        icon = if (isPositive) {
                            ImageVector.vectorResource(id = R.drawable.trending)
                        } else {
                            ImageVector.vectorResource(id = R.drawable.trending_down)
                        },
                        contentColor = contentColor
                    )
                } else {
                    InfoCard(
                        title = stringResource(R.string.change_last_24h),
                        formattedText = "Unknown",
                        icon = Icons.Default.CloudOff,
                        contentColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }

            var maxDataSize by remember { mutableIntStateOf(0) }

            if (coin.coinPriceHistory.isNotEmpty() && columnWidthPx != 0) {
                var visibleDataSize = coin.coinPriceHistory.lastIndex
                while (visibleDataSize > 0) {
                    val density = LocalDensity.current.density
                    val localTextStyle = LocalTextStyle.current
                    val textMeasurer = rememberTextMeasurer()

                    val columnWidthDp = remember(columnWidthPx, density) {
                        (columnWidthPx / density).toInt()
                    }

                    val columnWidthDpWithoutPadding = remember(columnWidthDp) {
                        columnWidthDp - (16 * 2)
                    }

                    val startIndex = remember(coin.coinPriceHistory, visibleDataSize) {
                        coin.coinPriceHistory.lastIndex - visibleDataSize
                    }
                    val lastIndex = remember(coin.coinPriceHistory) {
                        coin.coinPriceHistory.lastIndex
                    }

                    val visibleData = remember(startIndex, lastIndex) {
                        coin.coinPriceHistory.slice((startIndex..lastIndex))
                    }

                    val maxYValue = remember(visibleData) {
                        visibleData.maxOfOrNull { it.y } ?: 0f
                    }

                    val maxYValueFormatted = remember(maxYValue) {
                        ValueLabel(
                            value = maxYValue,
                            unit = chartStyle.unit
                        ).formatted()
                    }

                    val maxYValueWidthDp = remember(
                        maxYValueFormatted,
                        density
                    ) {
                        (textMeasurer.measure(
                            text = maxYValueFormatted,
                            style = localTextStyle.copy(
                                fontSize = chartStyle.labelFontSize,
                                color = chartStyle.textColor,
                                textAlign = TextAlign.Center
                            )
                        ).size.width / density).toInt()
                    }

                    val maxYValueWidthWithPadding = remember(maxYValueWidthDp) {
                        maxYValueWidthDp + 12
                    }

                    val columnWidthWithoutMaxYValue = remember(
                        columnWidthDpWithoutPadding,
                        maxYValueWidthWithPadding
                    ) {
                        columnWidthDpWithoutPadding - maxYValueWidthWithPadding
                    }

                    val xLabelsWidthDp = remember(
                        visibleData,
                        density
                    ) {
                        visibleData.map {
                            (textMeasurer.measure(
                                text = it.xLabel,
                                style = localTextStyle.copy(
                                    fontSize = chartStyle.labelFontSize,
                                    color = chartStyle.textColor,
                                    textAlign = TextAlign.Center
                                )
                            ).size.width / density).toInt()
                        }
                    }

                    val xLabelsRealWidthSum = remember(
                        xLabelsWidthDp
                    ) {
                        xLabelsWidthDp.mapIndexed { index, number ->
                            if (index == xLabelsWidthDp.lastIndex) {
                                number
                            } else {
                                number + 20
                            }
                        }.sum()
                    }

                    if (xLabelsRealWidthSum > columnWidthWithoutMaxYValue) {
                        visibleDataSize -= 1
                    } else {
                        maxDataSize = visibleDataSize
                        break
                    }
                }
            }

            val startIndex = remember(maxDataSize) {
                coin.coinPriceHistory.lastIndex - maxDataSize
            }

            AnimatedVisibility(
                visible = (coin.coinPriceHistory.isNotEmpty() && maxDataSize > 0),
                modifier = Modifier
                    .padding(top = 40.dp),
                exit = ExitTransition.None
            ) {
                LineChart(
                    dataPoints = coin.coinPriceHistory,
                    style = chartStyle,
                    visibleDataPointsIndices = startIndex..coin.coinPriceHistory.lastIndex,
                    unit = chartStyle.unit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                )
            }

        }
    }
}

@PreviewLightDark
@Composable
private fun CoinDetailScreenPreview() {
    CryptoTrackerTheme {
        CoinDetailScreen(
            state = CoinListState(
                selectedCoin = previewCoin
            ),
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}