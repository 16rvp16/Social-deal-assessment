package com.assessment.socialdeal.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Currency(
    @SerialName("symbol")
    val symbol: String,
    @SerialName("code")
    val code: CurrencyCode
)

@Serializable
enum class CurrencyCode(val symbol: String) {
    @SerialName("EUR")
    Euro(symbol = "€"),
    @SerialName("USD")
    Dollar(symbol = "$");

    /**
     * Retrieves the exchange rate that needs to be applied to a amount in this currency to reach
     * a amount in the given currency
     */
    fun getExchangeRateForCurrency(currencyCode: CurrencyCode): Double {
        return when (this) {
            Euro -> {
                when (currencyCode) {
                    Euro -> 1.0
                    Dollar -> EUR_TO_USD_EXCHANGE_RATE
                }
            }

            Dollar -> {
                when (currencyCode) {
                    Euro -> 1 / EUR_TO_USD_EXCHANGE_RATE
                    Dollar -> 1.0
                }
            }
        }
    }

    /**
     * For simplicity the exchange rates are static at the moment
     */
    private companion object ExchangeRates {
        const val EUR_TO_USD_EXCHANGE_RATE = 1.08
    }
}
