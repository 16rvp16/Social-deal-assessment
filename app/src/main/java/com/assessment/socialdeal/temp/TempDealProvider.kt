package com.assessment.socialdeal.temp

import com.assessment.socialdeal.model.Currency
import com.assessment.socialdeal.model.CurrencyCode
import com.assessment.socialdeal.model.Deal
import com.assessment.socialdeal.model.Price
import com.assessment.socialdeal.model.PricingInformation

//TODO delete this class
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

    /**
     * Get an [Deal] with the given [Deal.unique].
     */
    fun get(unique: String): Deal? {
        return allDeals.firstOrNull { it.unique == unique }
    }
}