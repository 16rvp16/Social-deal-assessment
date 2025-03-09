package com.assessment.socialdeal.fake

import com.assessment.socialdeal.data.DataResult
import com.assessment.socialdeal.data.DealsRepository
import com.assessment.socialdeal.model.Currency
import com.assessment.socialdeal.model.CurrencyCode
import com.assessment.socialdeal.model.Deal
import com.assessment.socialdeal.model.DealsPage
import com.assessment.socialdeal.model.Price
import com.assessment.socialdeal.model.PricingInformation

/**
 * Repository implementation with fake data for testing
 */
class FakeDealsRepository : DealsRepository {

    private val allDeals = listOf(
        Deal(
            unique = "unique1",
            title = "Deal nummer 1",
            image = "/deal/corendon-village-hotel-amsterdam-22113009143271.jpg",
            soldLabel = "Verkocht: 24",
            company = "Company",
            description = "Heb je zin in een deal? Dan is hier een beschrijving!",
            city = "Eindhoven",
            pricingInformation = PricingInformation(price = Price(5000, Currency(symbol = "€", code = CurrencyCode.Euro)), fromPrice = Price(10000, Currency(symbol = "€", code = CurrencyCode.Euro)), discountLabel = "50%"),
        ),
        Deal(
            unique = "unique2",
            title = "Deal nummer 2",
            image = "/deal/corendon-village-hotel-amsterdam-22113009143271.jpg",
            soldLabel = "Verkocht: 24",
            company = "Company",
            description = "Heb je zin in een deal? Dan is hier een beschrijving!",
            city = "Eindhoven",
            pricingInformation = PricingInformation(price = Price(5000, Currency(symbol = "€", code = CurrencyCode.Euro)), fromPrice = Price(10000, Currency(symbol = "€", code = CurrencyCode.Euro)), discountLabel = "50%"),
        ),
        Deal(
            unique = "unique3",
            title = "Deal nummer 3",
            image = "/deal/corendon-village-hotel-amsterdam-22113009143271.jpg",
            soldLabel = "Verkocht: 24",
            company = "Company",
            description = "Heb je zin in een deal? Dan is hier een beschrijving!",
            city = "Eindhoven",
            pricingInformation = PricingInformation(price = Price(5000, Currency(symbol = "€", code = CurrencyCode.Euro)), fromPrice = Price(10000, Currency(symbol = "€", code = CurrencyCode.Euro)), discountLabel = "50%"),
        ),
        Deal(
            unique = "unique4",
            title = "Deal nummer 4",
            image = "/deal/corendon-village-hotel-amsterdam-22113009143271.jpg",
            soldLabel = "Verkocht: 24",
            company = "Company",
            description = "Heb je zin in een deal? Dan is hier een beschrijving!",
            city = "Eindhoven",
            pricingInformation = PricingInformation(price = Price(5000, Currency(symbol = "€", code = CurrencyCode.Euro)), fromPrice = Price(10000, Currency(symbol = "€", code = CurrencyCode.Euro)), discountLabel = "50%"),
        ),
        Deal(
            unique = "unique5",
            title = "Deal nummer 5",
            image = "/deal/corendon-village-hotel-amsterdam-22113009143271.jpg",
            soldLabel = "Verkocht: 24",
            company = "Company",
            description = "Heb je zin in een deal? Dan is hier een beschrijving!",
            city = "Eindhoven",
            pricingInformation = PricingInformation(price = Price(5000, Currency(symbol = "€", code = CurrencyCode.Euro)), fromPrice = Price(10000, Currency(symbol = "€", code = CurrencyCode.Euro)), discountLabel = "50%"),
        ),
    )

    override suspend fun getDeals(): DataResult<DealsPage> {
        return DataResult.Success(DealsPage(allDeals.size, allDeals))
    }

    override suspend fun getDetails(deal: Deal): DataResult<Deal> {
        return DataResult.Success(deal)
    }

}