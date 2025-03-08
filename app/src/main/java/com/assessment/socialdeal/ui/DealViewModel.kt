package com.assessment.socialdeal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.assessment.socialdeal.DealsApplication
import com.assessment.socialdeal.data.DealsRepository
import com.assessment.socialdeal.data.UserPreferencesRepository
import com.assessment.socialdeal.model.CurrencyCode
import com.assessment.socialdeal.model.Deal
import com.assessment.socialdeal.model.NavigationItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.EnumMap

class DealViewModel(
    private val dealsRepository: DealsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DealsApplication)
                DealViewModel(
                    dealsRepository = application.container.dealsRepository,
                    userPreferencesRepository = application.container.userPreferencesRepository
                )
            }
        }
    }

    private val _uiState = MutableStateFlow(DealUiState())
    val uiState: StateFlow<DealUiState> = _uiState

    private val favoriteDealUniques: MutableSet<String> = HashSet()
    private var allDeals: List<Deal> = emptyList()

    init {
        viewModelScope.launch {
            userPreferencesRepository.preferredCurrency.collect { currencyCode ->
                _uiState.update { dealUiState ->
                    dealUiState.copy(
                        preferredCurrency = currencyCode,
                    )
                }
            }
        }
        getDeals()
    }

    private fun getDeals() {
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

    private fun getDetails(deal: Deal) {
        viewModelScope.launch {

            try {
                val dealDetails = dealsRepository.getDetails(deal)
                onDetailsUpdated(dealDetails = dealDetails)
            } catch (e: IOException) {
                //TODO error
            } catch (e: HttpException) {
                //TODO error
            }
        }
    }

    private fun onDealsUpdated(updatedDeals: List<Deal> = allDeals) {
        allDeals = updatedDeals
        val dealLists: MutableMap<NavigationItem, List<Deal>> = EnumMap(NavigationItem::class.java)
        dealLists[NavigationItem.All] = updatedDeals
        dealLists[NavigationItem.Favorites] =
            updatedDeals.filter { favoriteDealUniques.contains(it.unique) }

        _uiState.update { dealUiState ->
            dealUiState.copy(
                dealCategories = dealLists,
                favoriteDealUniques = favoriteDealUniques
            )
        }
    }

    private fun onDetailsUpdated(dealDetails: Deal) {
        _uiState.update { dealUiState ->
            // Update the details if this deal is still selected
            // otherwise we do nothing
            if (dealUiState.currentSelectedDeal?.unique == dealDetails.unique) {
                dealUiState.copy(
                    currentSelectedDeal = dealDetails
                )
            } else {
                dealUiState
            }
        }

    }

    fun updateCurrentNavigationItem(navigationItem: NavigationItem) {
        _uiState.update { dealUiState ->
            dealUiState.copy(
                currentNavigationItem = navigationItem,
                currentSelectedDeal = null
            )
        }
    }

    fun updateDetailsScreenStates(deal: Deal) {
        _uiState.update { dealUiState ->
            dealUiState.copy(
                currentSelectedDeal = deal,
                isShowingDealDetails = true
            )
        }
        getDetails(deal)
    }

    fun closeDetailsScreen() {
        _uiState.update { dealUiState ->
            dealUiState.copy(
                currentSelectedDeal = null,
                isShowingDealDetails = false
            )
        }
    }

    fun updateDealFavorite(deal: Deal, isFavorite: Boolean) {
        if (if (isFavorite) {
                favoriteDealUniques.add(deal.unique)
            } else {
                favoriteDealUniques.remove(deal.unique)
            }
        ) {
            onDealsUpdated()
        }
    }

    fun selectPreferredCurrency(currencyCode: CurrencyCode) {
        viewModelScope.launch {
            userPreferencesRepository.savePreferredCurrency(currencyCode)
        }
    }
}