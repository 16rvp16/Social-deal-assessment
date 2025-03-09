package com.assessment.socialdeal.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement.End
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImagePainter
import coil3.compose.AsyncImagePainter.State.Error
import coil3.compose.AsyncImagePainter.State.Loading
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.assessment.socialdeal.R
import com.assessment.socialdeal.model.CurrencyCode
import com.assessment.socialdeal.model.Deal
import com.assessment.socialdeal.model.PricingInformation
import com.assessment.socialdeal.utils.conditional
import com.assessment.socialdeal.utils.formatPrice

/**
 * Composes a Column that includes a summary of the given [Deal]
 * The summery includes: Title, company, city, amount sold, original price and price
 */
@Composable
fun DealSummary(
    dealUiState: DealUiState,
    deal: Deal,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    )
    {
        Text(
            text = deal.title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.text_padding_large),
                    bottom = dimensionResource(id = R.dimen.text_padding_large)
                ),
        )
        Text(
            text = deal.company,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.text_padding_small)),
        )
        Text(
            text = deal.city,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.text_padding)),
        )
        Row(
            horizontalArrangement = End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = deal.soldLabel,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                color = colorResource(id = R.color.sold_highlight),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
            )
            DealPricingLabels(
                pricingInformation = deal.pricingInformation,
                preferredCurrencyCode = dealUiState.preferredCurrency
            )
        }
    }
}

/**
 * Composes a Row with [PricingInformation]
 * The original price is displayed if present
 * The current price is always displayed
 */
@Composable
fun DealPricingLabels(
    pricingInformation: PricingInformation,
    preferredCurrencyCode: CurrencyCode,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        pricingInformation.fromPrice?.let { fromPrice ->
            Text(
                text = formatPrice(fromPrice, preferredCurrencyCode),
                style = MaterialTheme.typography.bodyLarge.plus(TextStyle(textDecoration = TextDecoration.LineThrough)),
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.text_padding))
                    .align(Alignment.CenterVertically)
            )
        }
        Text(
            text = formatPrice(pricingInformation.price, preferredCurrencyCode),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium,
            color = colorResource(id = R.color.price_highlight),
            modifier = Modifier
        )
    }
}


/**
 * Composes an button that represents the current favorite state of the given [Deal]
 */
@Composable
fun DealFavoriteToggleButton(
    dealUiState: DealUiState,
    deal: Deal,
    onFavoriteToggled: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val checked = dealUiState.isDealFavorite(deal)
    IconToggleButton(
        checked = checked,
        onCheckedChange = onFavoriteToggled,
        modifier = modifier
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                tint = colorResource(id = R.color.red),
                contentDescription = stringResource(
                    id = R.string.favorite_icon_description
                )
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                tint = colorResource(id = R.color.white),
                contentDescription = stringResource(
                    id = R.string.not_favorite_icon_description
                )
            )
        }
    }
}

/**
 * Composes the image for the given [Deal]
 * @param imageOverlay A composable that is draw in a [BoxScope] on top of the image
 */
@Composable
fun DealImage(
    deal: Deal,
    roundedCorners: Boolean = true,
    imageOverlay: @Composable BoxScope.(AsyncImagePainter.State) -> Unit = { }
) {
    // Use a separate image painter so the we can easily respond to changes in the image state
    // for redrawing
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(deal.prefixedImage)
            .crossfade(true)
            .build()
    )

    val painterState = painter.state.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeightIn(
                min = dimensionResource(id = R.dimen.deal_image_min_height),
                max = dimensionResource(id = R.dimen.deal_image_max_height)
            )
            .zIndex(-1f)
    ) {
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.deal_photo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .conditional(roundedCorners, {
                    clip(RoundedCornerShape(size = dimensionResource(id = R.dimen.deal_image_corner_radius)))
                })
        )
        // Switch on the current state of the image painter
        // When it is loading we draw a progress indicator
        // When it fails we draw a error icon
        when (painterState.value) {
            is Loading -> CircularProgressIndicator(
                modifier = Modifier
                    .align(alignment = Alignment.Center)
            )

            is Error -> Icon(
                painter = painterResource(R.drawable.ic_broken_image),
                tint = colorResource(id = R.color.grey),
                contentDescription = stringResource(R.string.load_image_error_description),
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .size(size = dimensionResource(id = R.dimen.deal_image_min_height))
            )

            else -> {}
        }
        // When the painter state is update we also have to redraw the overlay, to ensure that
        // it is always on top
        imageOverlay(painterState.value)
    }
}
