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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.assessment.socialdeal.R
import com.assessment.socialdeal.data.Deal
import com.assessment.socialdeal.data.DealCategory

@Composable
fun DealListScreen(
    dealUiState: DealUiState,
    onDealCardPressed: (Deal) -> Unit,
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
        navigationItemContentList = navigationItemContentList,
        modifier = modifier
    )
}

@Composable
private fun DealListScreenContent(
    dealUiState: DealUiState,
    onDealCardPressed: (Deal) -> Unit,
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
                deal = deal,
                selected = false,
                onCardClicked = {
                    onDealCardPressed(deal)
                }
            )
        }
    }
}

@Composable
fun DealListItem(
    deal: Deal,
    selected: Boolean,
    onCardClicked: () -> Unit,
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

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(deal.prefixedImage)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                contentDescription = stringResource(R.string.deal_photo),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(size = 16.dp))
            )
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
            Row (
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
                Text(
                    text = deal.pricingInformation.fromPrice.amount.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                )
                Text(
                    text = deal.pricingInformation.price.amount.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
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
