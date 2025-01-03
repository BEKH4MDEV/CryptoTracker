package com.plcoding.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.presentation.models.CoinUi
import com.plcoding.cryptotracker.crypto.presentation.models.toCoinUi
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinListItem(
    modifier: Modifier = Modifier,
    coinUi: CoinUi,
    onClick: () -> Unit,
) {

    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(16.dp)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Icon(
            imageVector = ImageVector.vectorResource(id = coinUi.iconRes),
            contentDescription = coinUi.name,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(85.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = coinUi.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = coinUi.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$ ${coinUi.priceUsd.formatted}",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            PriceChange(
                change = coinUi.changePercent24H
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinListItemPreview() {
    CryptoTrackerTheme {
        CoinListItem(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            coinUi = previewCoin,
            onClick = {}
        )
    }
}

internal val previewCoin = Coin(
    id = "id",
    rank = 1,
    name = "Bitcoin",
    symbol = "BTC",
    marketCapUsd = 84847787774883.75,
    priceUsd = 62_828.15,
    changePercent24H = -0.1
).toCoinUi()

