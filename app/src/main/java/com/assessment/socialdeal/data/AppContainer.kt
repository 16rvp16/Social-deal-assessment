package com.assessment.socialdeal.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.assessment.socialdeal.network.DealsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val dealsRepository: DealsRepository
    val userPreferencesRepository: UserPreferencesRepository
}

/**
 * Manual Dependency Injection container at the application level.
 */
class DefaultAppContainer(val dataStore: DataStore<Preferences>) : AppContainer {
    private val baseApiUrl = "https://media.socialdeal.nl/demo/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseApiUrl)
        .build()

    private val dealsApiService: DealsApiService by lazy {
        retrofit.create(DealsApiService::class.java)
    }

    override val dealsRepository: DealsRepository by lazy {
        NetworkDealsRepository(dealsApiService)
    }

    override val userPreferencesRepository: UserPreferencesRepository by lazy {
        DataStoreUserPreferencesRepository(dataStore)
    }

}