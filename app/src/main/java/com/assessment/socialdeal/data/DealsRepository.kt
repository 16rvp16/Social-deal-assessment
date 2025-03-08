package com.assessment.socialdeal.data

import com.assessment.socialdeal.model.Deal
import com.assessment.socialdeal.model.DealsPage
import com.assessment.socialdeal.network.DealsApiService

interface DealsRepository {
    suspend fun getDeals(): DealsPage
    suspend fun getDetails(unique: String): Deal
}

class NetworkDealsRepository(private val dealsApiService: DealsApiService) : DealsRepository {

    override suspend fun getDeals(): DealsPage {
        return dealsApiService.getDeals()
    }

    override suspend fun getDetails(unique: String): Deal {
        return dealsApiService.getDetails(unique)
    }
}