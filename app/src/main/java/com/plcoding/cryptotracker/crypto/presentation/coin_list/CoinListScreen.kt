package com.plcoding.cryptotracker.crypto.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.plcoding.cryptotracker.crypto.presentation.coin_list.components.CoinListItem
import com.plcoding.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinListScreen(
    modifier: Modifier = Modifier,
    onAction: (CoinListAction) -> Unit,
    state: CoinListState
) {
    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {
            items(state.coins) {
                CoinListItem(
                    coinUi = it,
                    onClick = {
                        onAction(CoinListAction.OnCoinClick(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider()
            }

        }
    }
}

@PreviewLightDark
@Composable
private fun CoinListScreenPreview() {
    CryptoTrackerTheme {
        CoinListScreen(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            state = CoinListState(
                coins = (1..100).map {
                    previewCoin.copy(id = it.toString())
                }
            ),
            onAction = {}
        )
    }
}