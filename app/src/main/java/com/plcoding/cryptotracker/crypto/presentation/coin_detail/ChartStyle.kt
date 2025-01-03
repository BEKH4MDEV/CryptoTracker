package com.plcoding.cryptotracker.crypto.presentation.coin_detail

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

data class ChartStyle(
    val chartLineColor: Color,
    val chartLineThickness: Dp,
    val popUpContainerColor: Color,
    val popUpContentColor: Color,
    val textColor: Color,
    val gridColor: Color,
    val gridThickness: Dp,
    val fillLineColor: Color,
    val labelFontSize: TextUnit,
    val unit: String = "$"
)
