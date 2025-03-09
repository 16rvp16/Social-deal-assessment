package com.assessment.socialdeal.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DealApp(
    modifier: Modifier = Modifier,
) {
    val viewModel: DealViewModel = viewModel(factory = DealViewModel.Factory)
    val dealUiState = viewModel.uiState.collectAsState().value

    //Switch between detail and list screens
    if (dealUiState.isShowingDealDetails) {
        DealDetailScreen(
            dealUiState = dealUiState,
            onBackPressed = {
                viewModel.closeDetailsScreen()
            },
            onDealFavoriteToggled = { deal, favorite ->
                viewModel.updateDealFavorite(deal, favorite)
            },
            onLoadDetailsPressed = {deal ->
                viewModel.getDetails(deal)
            },
            modifier = modifier
        )
    } else {
        DealListScreen(
            dealUiState = dealUiState,
            onTabPressed = { navigationItem ->
                viewModel.updateCurrentNavigationItem(navigationItem = navigationItem)
            },
            onDealCardPressed = { deal ->
                viewModel.updateDetailsScreenStates(deal = deal)
            },
            onDealFavoriteToggled = { deal, favorite ->
                viewModel.updateDealFavorite(deal, favorite)
            },
            onPreferredCurrencyChanged = { currencyCode ->
                viewModel.selectPreferredCurrency(currencyCode)
            },
            onLoadDealsPressed = {
                viewModel.getDeals()
            },
            modifier = modifier
        )
    }
}