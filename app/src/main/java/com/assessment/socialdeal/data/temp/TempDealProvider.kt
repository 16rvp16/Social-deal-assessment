package com.assessment.socialdeal.data.temp

import com.assessment.socialdeal.data.Currency
import com.assessment.socialdeal.data.CurrencyCode
import com.assessment.socialdeal.data.Deal
import com.assessment.socialdeal.data.Price
import com.assessment.socialdeal.data.PricingInformation

object TempDealProvider {

    val allDeals = listOf(
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

    val favoriteDeals = listOf(allDeals[0], allDeals[3])

    /**
     * Get an [Deal] with the given [Deal.unique].
     */
    fun get(unique: String): Deal? {
        return allDeals.firstOrNull { it.unique == unique }
    }
}