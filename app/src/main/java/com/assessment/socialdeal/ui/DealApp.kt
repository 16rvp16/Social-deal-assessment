package com.assessment.socialdeal.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DealApp(
    modifier: Modifier = Modifier,
) {
    val viewModel: DealViewModel = viewModel()
    val dealUiState = viewModel.uiState.collectAsState().value

    DealListScreen(
        dealUiState = dealUiState,
        onTabPressed = { dealCategory ->
            viewModel.updateCurrentDealCategory(dealCategory = dealCategory)
        },
        onDealCardPressed = { deal ->
            viewModel.updateDetailsScreenStates(deal = deal)
        },
        onDetailScreenBackPressed = {
            viewModel.closeDetailsScreen()
        },
        onDealFavoriteToggled = { deal, favorite ->
            viewModel.updateDealFavorite(deal, favorite)
        },
        modifier = modifier
    )
}