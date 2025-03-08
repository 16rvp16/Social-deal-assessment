package com.assessment.socialdeal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.assessment.socialdeal.DealsApplication
import com.assessment.socialdeal.data.DealsRepository
import com.assessment.socialdeal.model.Deal
import com.assessment.socialdeal.model.DealCategory
import com.assessment.socialdeal.temp.TempDealProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.EnumMap

class DealViewModel(private val dealsRepository: DealsRepository) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DealsApplication)
                val dealsRepository = application.container.dealsRepository
                DealViewModel(dealsRepository = dealsRepository)
            }
        }
    }

    private val _uiState = MutableStateFlow(DealUiState())
    val uiState: StateFlow<DealUiState> = _uiState

    private val favoriteDealUniques: MutableSet<String> = HashSet()
    private var allDeals: List<Deal> = emptyList()

    init {
        initializeUiState()
    }

    private fun initializeUiState() {
        getDeals()
    }

    fun getDeals() {
        viewModelScope.launch {

            try {
                val dealPage = dealsRepository.getDeals()
                onDealsUpdated(dealPage.deals)
            } catch (e: IOException) {
                //TODO error
            } catch (e: HttpException) {
                //TODO error
            }
        }
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