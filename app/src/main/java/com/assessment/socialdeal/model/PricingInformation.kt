package com.assessment.socialdeal.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PricingInformation(
    @SerialName("price")
    val price: Price,
    @SerialName("from_price")
    val fromPrice: Price? = null,
    @SerialName("price_label")
    val priceLabel: String? = null,
    @SerialName("discount_label")
    val discountLabel: String? = null
)
