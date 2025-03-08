package com.assessment.socialdeal.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DealsPage(
    @SerialName("num_deals")
    val numberOfDeals: Int,
    @SerialName("deals")
    val deals: List<Deal>
)