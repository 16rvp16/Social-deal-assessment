package com.assessment.socialdeal.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Deal(
    @SerialName("unique")
    val unique: String,
    @SerialName("title")
    val title: String,
    @SerialName("image")
    val image: String,
    @SerialName("sold_label")
    val soldLabel: String, // This contains translated text :(
    @SerialName("company")
    val company: String,
    @SerialName("description")
    val description: String? = null,
    @SerialName("city")
    val city: String,
    @SerialName("prices")
    val pricingInformation: PricingInformation,
) {
    val prefixedImage : String by lazy { "https://images.socialdeal.nl$image" }
}
