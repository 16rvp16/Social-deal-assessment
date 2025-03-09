package com.assessment.socialdeal

import com.assessment.socialdeal.fake.FakeDealsRepository
import com.assessment.socialdeal.fake.FakeUserPreferencesRepository
import com.assessment.socialdeal.model.CurrencyCode
import com.assessment.socialdeal.rules.TestDispatcherRule
import com.assessment.socialdeal.ui.DealViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class DealViewModelUnitTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()


    @Test
    fun dealViewModel_selectPreferredCurrency_verifyDealUiStateSuccess() {
        val dealViewModel = DealViewModel(
            dealsRepository = FakeDealsRepository(),
            userPreferencesRepository = FakeUserPreferencesRepository(),
        )

        //Test switch to USD
        dealViewModel.selectPreferredCurrency(CurrencyCode.USDollar)
        assertEquals(
            "The viewmodel state should have USD as preferred currency",
            CurrencyCode.USDollar.code,
            dealViewModel.uiState.value.preferredCurrency.code,
        )

        //Test switch to EUR
        dealViewModel.selectPreferredCurrency(CurrencyCode.Euro)
        assertEquals(
            "The viewmodel state should have EUR as preferred currency",
            CurrencyCode.Euro.code,
            dealViewModel.uiState.value.preferredCurrency.code,
        )
    }
}