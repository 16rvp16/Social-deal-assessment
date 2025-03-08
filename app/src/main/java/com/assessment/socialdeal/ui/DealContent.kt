package com.assessment.socialdeal.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement.End
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
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

@Composable
fun DealHeader(
    dealUiState: DealUiState,
    deal: Deal,
    imageHasRoundedCorners: Boolean = true,
    onFavoriteToggled: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DealImage(deal = deal, roundedCorners = imageHasRoundedCorners) {
                DealFavoriteToggleButton(
                    dealUiState = dealUiState,
                    deal = deal,
                    onFavoriteToggled = onFavoriteToggled,
                    modifier = Modifier
                        .align(alignment = Alignment.BottomEnd)
                        .zIndex(1f)
                )
            }
        }
        Text(
            text = deal.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(top = 12.dp, bottom = 12.dp),
        )
        Text(
            text = deal.company,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(bottom = 4.dp),
        )
        Text(
            text = deal.city,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(bottom = 8.dp),
        )
        Row(
            horizontalArrangement = End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = deal.soldLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .weight(1f),
            )
            DealPricingLabels(
                pricingInformation = deal.pricingInformation,
                preferredCurrencyCode = CurrencyCode.Euro
            )
        }
    }
}

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
                style = MaterialTheme.typography.bodyMedium.plus(TextStyle(textDecoration = TextDecoration.LineThrough)),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(end = 8.dp)
            )
        }
        Text(
            text = formatPrice(pricingInformation.price, preferredCurrencyCode),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
        )
    }
}

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

@Composable
fun DealImage(
    deal: Deal,
    roundedCorners: Boolean = true,
    imageOverlay: @Composable (AsyncImagePainter.State) -> Unit = { }
) {
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
            .requiredHeightIn(min = 50.dp, max = 250.dp)
            .zIndex(-1f)
    ) {
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.deal_photo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .conditional(roundedCorners, {
                    clip(RoundedCornerShape(size = 16.dp))
                })
        )
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
                    .size(size = 50.dp)
            )

            else -> {}
        }
        imageOverlay(painterState.value)
    }
}
