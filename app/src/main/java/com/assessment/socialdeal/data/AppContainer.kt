package com.assessment.socialdeal.data

import com.assessment.socialdeal.network.DealsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val dealsRepository: DealsRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 */
class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://media.socialdeal.nl/demo/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val dealsApiService: DealsApiService by lazy {
        retrofit.create(DealsApiService::class.java)
    }

    override val dealsRepository: DealsRepository by lazy {
        NetworkDealsRepository(dealsApiService)
    }
}