package com.assessment.socialdeal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.assessment.socialdeal.DealsApplication
import com.assessment.socialdeal.data.DealsRepository
import com.assessment.socialdeal.data.ResultState
import com.assessment.socialdeal.data.UserPreferencesRepository
import com.assessment.socialdeal.model.CurrencyCode
import com.assessment.socialdeal.model.Deal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    // These variables should not be here, they are used for combining deals data with favorites data
    // These and the code that currently does this is better placed in a different class in the domain layer
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

    /**
     * Uses the [DealsRepository] to fetch all deals
     */
    fun getDeals() {
        // Set the current state to loading deals
        _uiState.update { dealUiState ->
            dealUiState.copy(
                dealListDataRequestState = DataRequestState.Loading
            )
        }
        // Fetch the deals async
        viewModelScope.launch {
            val dealPageResult = dealsRepository.getDeals()
            if(dealPageResult.resultState == ResultState.Success && dealPageResult.result != null) {
                // Update the deals and the request state to success
                onDealsUpdated(updatedDeals = dealPageResult.result.deals)
                _uiState.update { dealUiState ->
                    dealUiState.copy(
                        dealListDataRequestState = DataRequestState.Success
                    )
                }
            } else {
                // Set the current state to failure to load the deals
                _uiState.update { dealUiState ->
                    dealUiState.copy(
                        dealListDataRequestState = DataRequestState.Failure
                    )
                }
            }
        }
    }

    /**
     * Uses the [DealsRepository] to fetch the details for a given [Deal]
     */
    fun getDetails(deal: Deal) {
        // Set the current state to loading deal details
        _uiState.update { dealUiState ->
            dealUiState.copy(
                dealDetailsDataRequestState = DataRequestState.Loading
            )
        }
        viewModelScope.launch {
            val dealDetailsResult = dealsRepository.getDetails(deal)
            if(dealDetailsResult.resultState == ResultState.Success && dealDetailsResult.result != null) {
                // Update the deal details and the request state to success
                onDetailsUpdated(dealDetails = dealDetailsResult.result)
                _uiState.update { dealUiState ->
                    dealUiState.copy(
                        dealDetailsDataRequestState = DataRequestState.Success
                    )
                }
            } else {
                // Set the current state to failure to load the deal details
                _uiState.update { dealUiState ->
                    dealUiState.copy(
                        dealDetailsDataRequestState = DataRequestState.Failure
                    )
                }
            }
        }
    }

    /**
     * Called when the list of all [Deal] has been updated, or when the favorites have been updated
     */
    private fun onDealsUpdated(updatedDeals: List<Deal> = allDeals) {
        allDeals = updatedDeals
        // Prepare a map with deals for each navigation item
        val dealLists: MutableMap<NavigationItem, List<Deal>> = EnumMap(NavigationItem::class.java)
        dealLists[NavigationItem.All] = updatedDeals
        dealLists[NavigationItem.Favorites] =
            updatedDeals.filter { favoriteDealUniques.contains(it.unique) }

        // Update the state with the new lists
        _uiState.update { dealUiState ->
            dealUiState.copy(
                dealCategories = dealLists,
                favoriteDealUniques = favoriteDealUniques
            )
        }
    }

    /**
     * Called when new details have been received for a given [Deal]
     */
    private fun onDetailsUpdated(dealDetails: Deal) {
        // Update the state with the new details
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

    /**
     * Called when the user selects a navigation item
     */
    fun updateCurrentNavigationItem(navigationItem: NavigationItem) {
        // Update the selected navigation item in the state
        _uiState.update { dealUiState ->
            dealUiState.copy(
                currentNavigationItem = navigationItem,
                currentSelectedDeal = null
            )
        }
    }

    /**
     * Called when the user selects a [Deal] to open
     */
    fun updateDetailsScreenStates(deal: Deal) {
        // Update the current state with selected deal
        _uiState.update { dealUiState ->
            dealUiState.copy(
                currentSelectedDeal = deal,
                isShowingDealDetails = true
            )
        }
        // Start retrieving details for this deal
        getDetails(deal)
    }

    /**
     * Called when the user closes the detail screen
     */
    fun closeDetailsScreen() {
        // Update the current state to reflect that the detail screen is closed
        _uiState.update { dealUiState ->
            dealUiState.copy(
                currentSelectedDeal = null,
                isShowingDealDetails = false
            )
        }
    }

    /**
     * Called when a [Deal] is added or removed from the favorites by the user
     */
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

    /**
     * Called when the user selects a new preferred [CurrencyCode]
     */
    fun selectPreferredCurrency(currencyCode: CurrencyCode) {
        viewModelScope.launch {
            userPreferencesRepository.savePreferredCurrency(currencyCode)
        }
    }
}