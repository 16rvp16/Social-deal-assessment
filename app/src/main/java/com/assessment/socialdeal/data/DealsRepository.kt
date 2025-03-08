package com.assessment.socialdeal.data

import com.assessment.socialdeal.model.Deal
import com.assessment.socialdeal.model.DealsPage
import com.assessment.socialdeal.network.DealsApiService

interface DealsRepository {
    suspend fun getDeals(): DealsPage
    suspend fun getDetails(deal: Deal): Deal
}

class NetworkDealsRepository(private val dealsApiService: DealsApiService) : DealsRepository {

    override suspend fun getDeals(): DealsPage {
        return dealsApiService.getDeals()
    }

    override suspend fun getDetails(deal: Deal): Deal {
        val dealDetails = dealsApiService.getDetails(deal.unique)

        // We are merging the original deal with the newly retrieved description here
        // The reason we do that here is because we know that the API is static, and this way the
        // result looks better in the app. Normally this method should take a unique as parameter
        return Deal(
            unique = deal.unique,
            title = deal.title,
            image = deal.image,
            soldLabel = deal.soldLabel,
            company = deal.company,
            description = dealDetails.description,
            city = deal.city,
            pricingInformation = deal.pricingInformation
        )
    }
}