package com.assessment.socialdeal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.zIndex
import com.assessment.socialdeal.R
import com.assessment.socialdeal.model.CurrencyCode
import com.assessment.socialdeal.model.Deal

/**
 * Composes a screen with a bottom tab navigation bar
 * The following tabs are included:
 * - A list of all [Deal]
 * - A list of all favorite [Deal]
 * - User preferences
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DealListScreen(
    dealUiState: DealUiState,
    onDealCardPressed: (Deal) -> Unit,
    onDealFavoriteToggled: (Deal, Boolean) -> Unit,
    onTabPressed: (NavigationItem) -> Unit,
    onPreferredCurrencyChanged: (CurrencyCode) -> Unit,
    onLoadDealsPressed: () -> Unit,
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
                    // The title of the top AppBar changes based on the currently selected tab
                    Text(
                        if (dealUiState.currentNavigationItem == NavigationItem.Preferences) {
                            stringResource(R.string.settings_navigation_title)
                        } else {
                            stringResource(R.string.deals_navigation_title)
                        }
                    )
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
            onLoadDealsPressed = onLoadDealsPressed,
            modifier = Modifier.padding(contentPadding)
        )
    }
}

/**
 * Composes the current screen content based on the selected tab
 */
@Composable
private fun DealListScreenContent(
    dealUiState: DealUiState,
    onDealCardPressed: (Deal) -> Unit,
    onDealFavoriteToggled: (Deal, Boolean) -> Unit,
    onPreferredCurrencyChanged: (CurrencyCode) -> Unit,
    onLoadDealsPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            if (dealUiState.currentNavigationItem == NavigationItem.Preferences) {
                PreferencesContent(
                    dealUiState = dealUiState,
                    onPreferredCurrencyChanged = onPreferredCurrencyChanged,
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            horizontal = dimensionResource(R.dimen.deal_list_padding)
                        )
                )
            } else {
                DealListContent(
                    dealUiState = dealUiState,
                    onDealCardPressed = onDealCardPressed,
                    onDealFavoriteToggled = onDealFavoriteToggled,
                    onLoadDealsPressed = onLoadDealsPressed,
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

/**
 * Composes the screen content for when the preferences tab is opened
 */
@Composable
fun PreferencesContent(
    dealUiState: DealUiState,
    onPreferredCurrencyChanged: (CurrencyCode) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        PreferredCurrencyPreference(dealUiState = dealUiState, onPreferredCurrencyChanged)
    }
}

/**
 * Composes a Card that contains a interactive preference that allows the user to select a preferred
 * currency
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferredCurrencyPreference(
    dealUiState: DealUiState,
    onPreferredCurrencyChanged: (CurrencyCode) -> Unit,
    modifier: Modifier = Modifier
) {
    // We need to remember the current state of the dropdown menu
    var currencyMenuOpened by remember { mutableStateOf(false) }
    Card(modifier = modifier) {
        Row {
            Text(
                text = stringResource(R.string.currency_preference_label),
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.text_padding))
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            )
            ExposedDropdownMenuBox(
                expanded = currencyMenuOpened,
                onExpandedChange = { currencyMenuOpened = it },
                modifier = Modifier.width(dimensionResource(id = R.dimen.preference_drop_down_width))
            ) {
                TextField(
                    modifier = Modifier.menuAnchor(),
                    value = dealUiState.preferredCurrency.commonName,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = currencyMenuOpened) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )
                ExposedDropdownMenu(
                    modifier = Modifier.exposedDropdownSize(matchTextFieldWidth = true),
                    expanded = currencyMenuOpened,
                    onDismissRequest = { currencyMenuOpened = false },
                ) {
                    CurrencyCodeDropDownMenuItem(
                        dealUiState = dealUiState,
                        currencyCode = CurrencyCode.Euro
                    ) { currencyCode ->
                        currencyMenuOpened = false
                        onPreferredCurrencyChanged(currencyCode)
                    }
                    CurrencyCodeDropDownMenuItem(
                        dealUiState = dealUiState,
                        currencyCode = CurrencyCode.USDollar
                    ) { currencyCode ->
                        currencyMenuOpened = false
                        onPreferredCurrencyChanged(currencyCode)
                    }
                }
            }
        }
    }
}

/**
 * Composes a Dropdown menu item for a given [CurrencyCode]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyCodeDropDownMenuItem(
    dealUiState: DealUiState,
    currencyCode: CurrencyCode,
    onCurrencySelected: (CurrencyCode) -> Unit,
) {
    DropdownMenuItem(
        text = {
            Text(
                text = currencyCode.commonName,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        onClick = {
            onCurrencySelected(currencyCode)
        },
        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
        modifier =
        Modifier.background(
            if (currencyCode == dealUiState.preferredCurrency) MaterialTheme.colorScheme.secondaryContainer
            else Color.Transparent
        ),
        colors = MenuDefaults.itemColors(),
    )
}

/**
 * Composes a list of [Deal] based on the currently selected tab
 */
@Composable
fun DealListContent(
    dealUiState: DealUiState,
    onDealCardPressed: (Deal) -> Unit,
    onDealFavoriteToggled: (Deal, Boolean) -> Unit,
    onLoadDealsPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dealList = dealUiState.currentDealList

    // Switches upon the current state of the request that loads all deals
    // If the request is still in progress or has failed, we will communicate that to the user
    // otherwise we display the list of [Deal]
    when (dealUiState.dealListDataRequestState) {
        DataRequestState.Success -> {
            // If the favorites tab has been selected, and there are no favorites yet
            // we will display a message to inform the user that they don't have any favorites yet
            if (dealUiState.currentNavigationItem == NavigationItem.Favorites && dealList.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_favorites_message),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
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
        }

        DataRequestState.Loading -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(
                    text = stringResource(R.string.retrieving_deals_progress_message),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        DataRequestState.Failure -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.retreive_details_error_message),
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.text_padding)),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Button(onClick = onLoadDealsPressed) {
                    Text(
                        text = stringResource(R.string.retry_label),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }
}

/**
 * Composes a Card that contains a image an summary information for a given [Deal]
 */
@Composable
fun DealListItem(
    dealUiState: DealUiState,
    deal: Deal,
    onCardClicked: () -> Unit,
    onFavoriteToggled: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        ),
        onClick = onCardClicked
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.deal_list_item_padding))
        )
        {
            DealImage(deal = deal, roundedCorners = true) {
                DealFavoriteToggleButton(
                    dealUiState = dealUiState,
                    deal = deal,
                    onFavoriteToggled = onFavoriteToggled,
                    modifier = Modifier
                        .align(alignment = Alignment.BottomEnd)
                        .zIndex(1f)
                )
            }
            DealSummary(dealUiState = dealUiState, deal = deal)
        }
    }
}

/**
 * Composes the bottom navigation bar for the DealList screen
 */
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
