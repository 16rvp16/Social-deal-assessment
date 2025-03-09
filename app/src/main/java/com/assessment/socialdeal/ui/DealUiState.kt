package com.assessment.socialdeal.ui

import com.assessment.socialdeal.model.CurrencyCode
import com.assessment.socialdeal.model.Deal

data class DealUiState(
    // Map that contains a list of deals to display for each navigation item
    val dealCategories: Map<NavigationItem, List<Deal>> = emptyMap(),
    // The currently selected navigation item
    val currentNavigationItem: NavigationItem = NavigationItem.All,
    // The currently selected deal
    val currentSelectedDeal: Deal? = null,
    // True if the detail screen should be opened
    val isShowingDealDetails: Boolean = false,
    // Set that contains all the uniques for each deal that has been marked as favorite
    val favoriteDealUniques: Set<String> = emptySet(),

    // The preferred currency
    val preferredCurrency: CurrencyCode = CurrencyCode.Euro,
    // [DataRequestState] for retrieving the list of deals
    val dealListDataRequestState: DataRequestState = DataRequestState.Success,
    // [DataRequestState] for retrieving the deal details
    val dealDetailsDataRequestState: DataRequestState = DataRequestState.Success,
) {
    // Fetches a list of deals for the currently selected navigation item
    val currentDealList: List<Deal> by lazy { dealCategories[currentNavigationItem] ?: emptyList() }

    // Checks if a given deal has been marked as a favorite
    fun isDealFavorite(deal: Deal) : Boolean {
        return favoriteDealUniques.contains(deal.unique)
    }
}

enum class DataRequestState {
    Loading,
    Success,
    Failure
}