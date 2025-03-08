package com.assessment.socialdeal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.assessment.socialdeal.R
import com.assessment.socialdeal.model.CurrencyCode
import com.assessment.socialdeal.model.Deal
import com.assessment.socialdeal.model.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DealListScreen(
    dealUiState: DealUiState,
    onDealCardPressed: (Deal) -> Unit,
    onDealFavoriteToggled: (Deal, Boolean) -> Unit,
    onTabPressed: (NavigationItem) -> Unit,
    onPreferredCurrencyChanged: (CurrencyCode) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navigationItemContentList = listOf(
        NavigationItemContent(
            navigationItem = NavigationItem.All,
            label = stringResource(id = R.string.tab_all),
            icon = Icons.Default.Home,
        ),
        NavigationItemContent(
            navigationItem = NavigationItem.Favorites,
            label = stringResource(id = R.string.tab_favorites),
            icon = Icons.Default.Favorite,
        ),
        NavigationItemContent(
            navigationItem = NavigationItem.Preferences,
            label = stringResource(id = R.string.tab_preferences),
            icon = Icons.Default.Settings,
        )
    )
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
                title = {
                    Text(stringResource(R.string.deals_navigation_title))
                }
            )
        },
        bottomBar = {
            DealBottomNavigationBar(
                currentTab = dealUiState.currentNavigationItem,
                navigationItemContentList = navigationItemContentList,
                onTabPressed = onTabPressed,
                modifier = Modifier.fillMaxWidth()
            )
        }) { contentPadding ->
        DealListScreenContent(
            dealUiState = dealUiState,
            onDealCardPressed = onDealCardPressed,
            onDealFavoriteToggled = onDealFavoriteToggled,
            onPreferredCurrencyChanged = onPreferredCurrencyChanged,
            modifier = Modifier.padding(contentPadding)
        )
    }
}

@Composable
private fun DealListScreenContent(
    dealUiState: DealUiState,
    onDealCardPressed: (Deal) -> Unit,
    onDealFavoriteToggled: (Deal, Boolean) -> Unit,
    onPreferredCurrencyChanged: (CurrencyCode) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            if (dealUiState.currentNavigationItem == NavigationItem.Preferences) {
                PreferencesContent(
                    dealUiState = dealUiState,
                    onPreferredCurrencyChanged = onPreferredCurrencyChanged
                )
            } else {
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
            }
        }
    }
}

@Composable
fun PreferencesContent(
    dealUiState: DealUiState,
    onPreferredCurrencyChanged: (CurrencyCode) -> Unit,
    modifier: Modifier = Modifier
) {
    var menuOpened by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier,
    ) {
        Card(onClick = { menuOpened = true }) {
            Row(modifier = modifier) {
                Text(
                    text = "Valuta",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(1f)
                )
                Text(
                    text = dealUiState.preferredCurrency.symbol,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                )
            }
            DropdownMenu(expanded = menuOpened, onDismissRequest = { menuOpened = false }) {
                DropdownMenuItem(
                    text = { Text(text = "Euro") },
                    onClick = {
                        menuOpened = false
                        onPreferredCurrencyChanged(CurrencyCode.Euro)
                    },
                    modifier =
                    Modifier.background(
                        if (CurrencyCode.Euro == dealUiState.preferredCurrency) MaterialTheme.colorScheme.secondaryContainer
                        else Color.Transparent
                    ),
                    colors = MenuDefaults.itemColors(),
                )
                DropdownMenuItem(
                    text = { Text(text = "Dollar") },
                    onClick = {
                        menuOpened = false
                        onPreferredCurrencyChanged(CurrencyCode.Dollar)
                    },
                    modifier =
                    Modifier.background(
                        if (CurrencyCode.Dollar == dealUiState.preferredCurrency) MaterialTheme.colorScheme.secondaryContainer
                        else Color.Transparent
                    ),
                    colors = MenuDefaults.itemColors(),
                )
            }
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
        DealHeader(
            dealUiState = dealUiState,
            deal = deal,
            onFavoriteToggled = onFavoriteToggled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.deal_list_item_padding))
        )
    }
}

@Composable
private fun DealBottomNavigationBar(
    currentTab: NavigationItem,
    navigationItemContentList: List<NavigationItemContent>,
    onTabPressed: ((NavigationItem) -> Unit),
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        for (navigationItem in navigationItemContentList) {
            NavigationBarItem(
                selected = currentTab == navigationItem.navigationItem,
                onClick = { onTabPressed(navigationItem.navigationItem) },
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
    val navigationItem: NavigationItem,
    val label: String,
    val icon: ImageVector
)
