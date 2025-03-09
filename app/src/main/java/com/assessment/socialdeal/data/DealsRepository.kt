package com.assessment.socialdeal.data

import com.assessment.socialdeal.model.Deal
import com.assessment.socialdeal.model.DealsPage
import com.assessment.socialdeal.network.DealsApiService
import retrofit2.HttpException
import java.io.IOException

interface DealsRepository {
    suspend fun getDeals(): DataResult<DealsPage>
    suspend fun getDetails(deal: Deal): DataResult<Deal>
}

class NetworkDealsRepository(private val dealsApiService: DealsApiService) : DealsRepository {

    /**
     * Uses the [DealsApiService] to retrieve a list of [Deal]
     */
    override suspend fun getDeals(): DataResult<DealsPage> {

        return try {
            DataResult.Success(dealsApiService.getDeals())
        } catch (e: IOException) {
            DataResult.Failure()
        } catch (e: HttpException) {
            DataResult.Failure()
        }
    }

    /**
     * Uses the [DealsApiService] to retrieve details for the specified [Deal]
     */
    override suspend fun getDetails(deal: Deal): DataResult<Deal> {
        return try {
            val dealDetails = dealsApiService.getDetails(deal.unique)

            // We are merging the original deal with the newly retrieved description here
            // The reason we do that here is because we know that the API is static, and this way the
            // result looks better in the app. Normally this method should take a unique as parameter
            DataResult.Success(
                Deal(
                    unique = deal.unique,
                    title = deal.title,
                    image = deal.image,
                    soldLabel = deal.soldLabel,
                    company = deal.company,
                    description = dealDetails.description,
                    city = deal.city,
                    pricingInformation = deal.pricingInformation
                )
            )
        } catch (e: IOException) {
            DataResult.Failure()
        } catch (e: HttpException) {
            DataResult.Failure()
        }
    }
}