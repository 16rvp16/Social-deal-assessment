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

    private val favoriteDealUniques: MutableSet<String> = HashSet()
    private var allDeals: List<Deal> = emptyList()

    init {
        initializeUiState()
    }

    private fun initializeUiState() {
        onDealsUpdated(TempDealProvider.allDeals)
    }

    private fun onDealsUpdated(updatedDeals: List<Deal> = allDeals) {
        allDeals = updatedDeals
        val dealLists: MutableMap<DealCategory, List<Deal>> = EnumMap(DealCategory::class.java)
        dealLists[DealCategory.All] = updatedDeals
        dealLists[DealCategory.Favorites] = updatedDeals.filter { favoriteDealUniques.contains(it.unique) }

        _uiState.update { dealUiState ->
            dealUiState.copy(
                dealCategories = dealLists,
                favoriteDealUniques = favoriteDealUniques
            )
        }
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

    fun updateDealFavorite(deal: Deal, isFavorite: Boolean) {
        if(if(isFavorite) {
            favoriteDealUniques.add(deal.unique)
        } else {
            favoriteDealUniques.remove(deal.unique)
        }) {
            onDealsUpdated()
        }
    }
}