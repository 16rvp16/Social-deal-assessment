package com.assessment.socialdeal.network

import com.assessment.socialdeal.model.Deal
import com.assessment.socialdeal.model.DealsPage
import retrofit2.http.GET
import retrofit2.http.Query

interface DealsApiService {

    /**
     * Returns a [DealsPage] that consist of a count and a list of [Deal]
     */
    @GET("deals.json")
    suspend fun getDeals(): DealsPage


    /**
     * Returns a more detailed [Deal] for the given unique
     */
    @GET("details.json")
    suspend fun getDetails(@Query("id") unique: String): Deal
}