package com.assessment.socialdeal.utils

import com.assessment.socialdeal.data.CurrencyCode
import com.assessment.socialdeal.data.Price
import java.util.Locale

/**
 * Formats the price into a readable [String] prefixed with the symbol from the [preferredCurrency]
 * Where the amount has been converted accordingly
 */
fun formatPrice(price: Price, preferredCurrency: CurrencyCode) : String {
    val convertedAmount = price.amountInCurrency * price.currency.code.getExchangeRateForCurrency(preferredCurrency)
    return "${preferredCurrency.symbol} ${String.format(locale = Locale.getDefault(), format = "%.2f", convertedAmount)}"
}