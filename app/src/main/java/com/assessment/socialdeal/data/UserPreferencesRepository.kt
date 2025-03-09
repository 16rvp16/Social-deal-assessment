package com.assessment.socialdeal.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.assessment.socialdeal.model.CurrencyCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

interface UserPreferencesRepository {
    val preferredCurrency: Flow<CurrencyCode>
    suspend fun savePreferredCurrency(currencyCode: CurrencyCode)
}

class DataStoreUserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) : UserPreferencesRepository {
    private companion object {
        val PREFERRED_CURRENCY = stringPreferencesKey("preferredCurrency")
        const val TAG = "DataStoreUserPreferencesRepository"
    }

    /**
     * Uses the [DataStore] to get a [Flow] of the preferred [CurrencyCode]
     */
    override val preferredCurrency: Flow<CurrencyCode> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            CurrencyCode.parseCode(preferences[PREFERRED_CURRENCY])
        }

    /**
     * Uses the [DataStore] to save a [CurrencyCode] as the preferred Currency
     */
    override suspend fun savePreferredCurrency(currencyCode: CurrencyCode) {
        dataStore.edit { preferences ->
            preferences[PREFERRED_CURRENCY] = currencyCode.code
        }
    }
}