package com.assessment.socialdeal.fake

import com.assessment.socialdeal.data.UserPreferencesRepository
import com.assessment.socialdeal.model.CurrencyCode
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeUserPreferencesRepository : UserPreferencesRepository {
    private val flow = MutableSharedFlow<CurrencyCode>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override val preferredCurrency: Flow<CurrencyCode>
        get() = flow

    override suspend fun savePreferredCurrency(currencyCode: CurrencyCode) {
        flow.emit(currencyCode)
    }
}