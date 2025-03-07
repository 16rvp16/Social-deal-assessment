package com.assessment.socialdeal.ui

import com.assessment.socialdeal.data.Deal
import com.assessment.socialdeal.data.DealCategory

data class DealUiState(
    val dealCategories: Map<DealCategory, List<Deal>> = emptyMap(),
    val currentDealCategory: DealCategory = DealCategory.All,
    val currentSelectedDeal: Deal? = null,
    val isShowingDealList: Boolean = true,
    val favoriteDealUniques: Set<String> = emptySet()
) {
    val currentDealList: List<Deal> by lazy { dealCategories[currentDealCategory] ?: emptyList() }

    fun isDealFavorite(deal: Deal) : Boolean {
        return favoriteDealUniques.contains(deal.unique)
    }
}