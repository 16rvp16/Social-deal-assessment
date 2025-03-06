package com.assessment.socialdeal.data.temp

import com.assessment.socialdeal.data.Currency
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
            pricingInformation = PricingInformation(price = Price(50.0, Currency(symbol = "€", code = "EUR")), fromPrice = Price(100.0, Currency(symbol = "€", code = "EUR")), discountLabel = "50%"),
        ),
        Deal(
            unique = "unique2",
            title = "Deal nummer 2",
            image = "/deal/corendon-village-hotel-amsterdam-22113009143271.jpg",
            soldLabel = "Verkocht: 24",
            company = "Company",
            description = "Heb je zin in een deal? Dan is hier een beschrijving!",
            city = "Eindhoven",
            pricingInformation = PricingInformation(price = Price(50.0, Currency(symbol = "€", code = "EUR")), fromPrice = Price(100.0, Currency(symbol = "€", code = "EUR")), discountLabel = "50%"),
        ),
        Deal(
            unique = "unique3",
            title = "Deal nummer 3",
            image = "/deal/corendon-village-hotel-amsterdam-22113009143271.jpg",
            soldLabel = "Verkocht: 24",
            company = "Company",
            description = "Heb je zin in een deal? Dan is hier een beschrijving!",
            city = "Eindhoven",
            pricingInformation = PricingInformation(price = Price(50.0, Currency(symbol = "€", code = "EUR")), fromPrice = Price(100.0, Currency(symbol = "€", code = "EUR")), discountLabel = "50%"),
        ),
        Deal(
            unique = "unique3",
            title = "Deal nummer 3",
            image = "/deal/corendon-village-hotel-amsterdam-22113009143271.jpg",
            soldLabel = "Verkocht: 24",
            company = "Company",
            description = "Heb je zin in een deal? Dan is hier een beschrijving!",
            city = "Eindhoven",
            pricingInformation = PricingInformation(price = Price(50.0, Currency(symbol = "€", code = "EUR")), fromPrice = Price(100.0, Currency(symbol = "€", code = "EUR")), discountLabel = "50%"),
        ),
        Deal(
            unique = "unique4",
            title = "Deal nummer 4",
            image = "/deal/corendon-village-hotel-amsterdam-22113009143271.jpg",
            soldLabel = "Verkocht: 24",
            company = "Company",
            description = "Heb je zin in een deal? Dan is hier een beschrijving!",
            city = "Eindhoven",
            pricingInformation = PricingInformation(price = Price(50.0, Currency(symbol = "€", code = "EUR")), fromPrice = Price(100.0, Currency(symbol = "€", code = "EUR")), discountLabel = "50%"),
        ),
    )

    /**
     * Get an [Deal] with the given [Deal.unique].
     */
    fun get(unique: String): Deal? {
        return allDeals.firstOrNull { it.unique == unique }
    }
}