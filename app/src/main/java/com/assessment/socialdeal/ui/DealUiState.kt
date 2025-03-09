package com.assessment.socialdeal.ui

import com.assessment.socialdeal.model.CurrencyCode
import com.assessment.socialdeal.model.Deal
import com.assessment.socialdeal.model.NavigationItem

data class DealUiState(
    val dealCategories: Map<NavigationItem, List<Deal>> = emptyMap(),
    val currentNavigationItem: NavigationItem = NavigationItem.All,
    val currentSelectedDeal: Deal? = null,
    val isShowingDealDetails: Boolean = false,
    val favoriteDealUniques: Set<String> = emptySet(),

    val preferredCurrency: CurrencyCode = CurrencyCode.Euro,
    val dealListDataRequestState: DataRequestState = DataRequestState.Success,
    val dealDetailsDataRequestState: DataRequestState = DataRequestState.Success,
) {
    val currentDealList: List<Deal> by lazy { dealCategories[currentNavigationItem] ?: emptyList() }

    fun isDealFavorite(deal: Deal) : Boolean {
        return favoriteDealUniques.contains(deal.unique)
    }
}

enum class DataRequestState {
    Loading,
    Success,
    Failure
}