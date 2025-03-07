package com.assessment.socialdeal.data

data class Price(
    val amountInCents: Int,
    val currency: Currency
) {
    val amountInCurrency: Double by lazy {amountInCents.toDouble() / 100.0}
}
