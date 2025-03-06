package com.assessment.socialdeal.data

data class Deal(
    val unique: String,
    val title: String,
    val image: String,
    val soldLabel: String, // This contains translated text :(
    val company: String,
    val description: String?,
    val city: String,
    val pricingInformation: PricingInformation,
) {
    val prefixedImage : String by lazy { "https://images.socialdeal.nl$image" }
}
