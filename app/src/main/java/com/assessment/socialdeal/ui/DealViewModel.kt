package com.assessment.socialdeal.ui

import androidx.lifecycle.ViewModel
import com.assessment.socialdeal.data.Deal
import com.assessment.socialdeal.data.DealCategory
import com.assessment.socialdeal.data.temp.TempDealProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.EnumMap

class DealViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DealUiState())
    val uiState: StateFlow<DealUiState> = _uiState

    init {
        initializeUiState()
    }

    private fun initializeUiState() {
        val dealLists: MutableMap<DealCategory, List<Deal>> = EnumMap(DealCategory::class.java)
        dealLists[DealCategory.All] = TempDealProvider.allDeals
        dealLists[DealCategory.Favorites] = TempDealProvider.favoriteDeals

        _uiState.value =
            DealUiState(
                dealCategories = dealLists,
                currentSelectedDeal = dealLists[DealCategory.All]?.first()
            )
    }

    fun updateCurrentDealCategory(dealCategory: DealCategory) {
        _uiState.update { dealUiState ->
            dealUiState.copy(
                currentDealCategory = dealCategory,
                currentSelectedDeal = null
            )
        }
    }

    fun updateDetailsScreenStates(deal: Deal) {
        _uiState.update { dealUiState ->
            dealUiState.copy(
                currentSelectedDeal = deal,
                isShowingDealList = false
            )
        }
    }

    fun closeDetailsScreen() {
        _uiState.update { dealUiState ->
            dealUiState.copy(
                currentSelectedDeal = null,
                isShowingDealList = true
            )
        }
    }
}