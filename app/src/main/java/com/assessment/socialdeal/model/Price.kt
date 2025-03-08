package com.assessment.socialdeal.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Price(
    @SerialName("amount")
    val amountInCents: Int,
    @SerialName("currency")
    val currency: Currency
) {
    val amountInCurrency: Double by lazy {amountInCents.toDouble() / 100.0}
}
