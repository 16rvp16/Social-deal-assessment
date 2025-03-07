package com.assessment.socialdeal.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.assessment.socialdeal.data.CurrencyCode
import com.assessment.socialdeal.data.PricingInformation
import com.assessment.socialdeal.utils.formatPrice

@Composable
fun DealPricingLabels(
    pricingInformation: PricingInformation,
    preferredCurrencyCode: CurrencyCode,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = formatPrice(pricingInformation.fromPrice, preferredCurrencyCode),
            style = MaterialTheme.typography.bodyMedium.plus(TextStyle(textDecoration = TextDecoration.LineThrough)),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(end = 8.dp)
        )
        Text(
            text = formatPrice(pricingInformation.price, preferredCurrencyCode),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
        )
    }
}
