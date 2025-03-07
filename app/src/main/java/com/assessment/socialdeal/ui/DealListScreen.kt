package com.assessment.socialdeal.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.End
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.assessment.socialdeal.R
import com.assessment.socialdeal.data.CurrencyCode
import com.assessment.socialdeal.data.Deal
import com.assessment.socialdeal.data.DealCategory

@Composable
fun DealListScreen(
    dealUiState: DealUiState,
    onDealCardPressed: (Deal) -> Unit,
    onDealFavoriteToggled: (Deal, Boolean) -> Unit,
    onDetailScreenBackPressed: () -> Unit,
    onTabPressed: (DealCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navigationItemContentList = listOf(
        NavigationItemContent(
            dealCategory = DealCategory.All,
            label = stringResource(id = R.string.tab_all),
            icon = Icons.Default.Home,
        ),
        NavigationItemContent(
            dealCategory = DealCategory.Favorites,
            label = stringResource(id = R.string.tab_favorites),
            icon = Icons.Default.Favorite,
        )
    )
    DealListScreenContent(
        dealUiState = dealUiState,
        onTabPressed = onTabPressed,
        onDealCardPressed = onDealCardPressed,
        onDealFavoriteToggled = onDealFavoriteToggled,
        navigationItemContentList = navigationItemContentList,
        modifier = modifier
    )
}

@Composable
private fun DealListScreenContent(
    dealUiState: DealUiState,
    onDealCardPressed: (Deal) -> Unit,
    onDealFavoriteToggled: (Deal, Boolean) -> Unit,
    onTabPressed: ((DealCategory) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            DealListContent(
                dealUiState = dealUiState,
                onDealCardPressed = onDealCardPressed,
                onDealFavoriteToggled = onDealFavoriteToggled,
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        horizontal = dimensionResource(R.dimen.deal_list_padding)
                    )
            )
            DealBottomNavigationBar(
                currentTab = dealUiState.currentDealCategory,
                navigationItemContentList = navigationItemContentList,
                onTabPressed = onTabPressed,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun DealListContent(
    dealUiState: DealUiState,
    onDealCardPressed: (Deal) -> Unit,
    onDealFavoriteToggled: (Deal, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val dealList = dealUiState.currentDealList

    LazyColumn(
        modifier = modifier,
        contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.deal_list_item_vertical_spacing)
        )
    ) {
        items(items = dealList, key = { deal -> deal.unique }) { deal ->
            DealListItem(
                dealUiState = dealUiState,
                deal = deal,
                selected = false,
                onCardClicked = {
                    onDealCardPressed(deal)
                },
                onFavoriteToggled = { favorite ->
                    onDealFavoriteToggled(deal, favorite)
                }
            )
        }
    }
}

@Composable
fun DealListItem(
    dealUiState: DealUiState,
    deal: Deal,
    selected: Boolean,
    onCardClicked: () -> Unit,
    onFavoriteToggled: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (selected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.secondaryContainer
        ),
        onClick = onCardClicked
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.deal_list_item_padding))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                DealImage(deal = deal) {
                    Log.d("Henk", "Drawing overlay")
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
}

@Composable
private fun DealBottomNavigationBar(
    currentTab: DealCategory,
    navigationItemContentList: List<NavigationItemContent>,
    onTabPressed: ((DealCategory) -> Unit),
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        for (navigationItem in navigationItemContentList) {
            NavigationBarItem(
                selected = currentTab == navigationItem.dealCategory,
                onClick = { onTabPressed(navigationItem.dealCategory) },
                icon = {
                    Icon(
                        imageVector = navigationItem.icon,
                        contentDescription = navigationItem.label
                    )
                }
            )
        }
    }
}

private data class NavigationItemContent(
    val dealCategory: DealCategory,
    val label: String,
    val icon: ImageVector
)
