package com.assessment.socialdeal.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DealApp (
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
    modifier = modifier
    )
}