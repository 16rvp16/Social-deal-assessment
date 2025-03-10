package com.assessment.socialdeal.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.zIndex
import com.assessment.socialdeal.R
import com.assessment.socialdeal.model.Deal

/**
 * Composes the detail screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DealDetailScreen(
    dealUiState: DealUiState,
    onBackPressed: () -> Unit,
    onDealFavoriteToggled: (Deal, Boolean) -> Unit,
    onLoadDetailsPressed: (Deal) -> Unit,
    modifier: Modifier = Modifier
) {
    // BackHandler so that we can register when the user navigates back, and close the detail screen
    // accordingly
    BackHandler(enabled = true, onBack = onBackPressed)

    Scaffold(topBar = {
        // The top AppBar contains a button to navigate back to the list screen
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
            title = {
                Text(dealUiState.currentSelectedDeal?.company ?: "")
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackPressed,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.navigation_back)
                    )
                }
            }
        )

    }, modifier = modifier)
    { contentPadding ->
        // The content of the screen exist out of a image, deal summary information and a detailed text
        LazyColumn(
            contentPadding = contentPadding,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            item {
                dealUiState.currentSelectedDeal?.let { deal ->
                    DealImage(deal = deal, roundedCorners = false) {
                        DealFavoriteToggleButton(
                            dealUiState = dealUiState,
                            deal = deal,
                            onFavoriteToggled = { favorite ->
                                onDealFavoriteToggled(
                                    deal,
                                    favorite
                                )
                            },
                            modifier = Modifier
                                .align(alignment = Alignment.BottomEnd)
                                .zIndex(1f)
                        )
                    }
                    DealSummary(
                        dealUiState = dealUiState,
                        deal = deal,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.deal_details_content_padding))
                    )

                    // Switch on the current state of the request that fetches the detailed information
                    // When the we are loading or the request fails we notify the user
                    // Otherwise we show the details
                    when (dealUiState.dealDetailsDataRequestState) {
                        DataRequestState.Success -> {
                            DealDetailsContent(
                                dealUiState = dealUiState, modifier = modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(id = R.dimen.deal_details_content_padding))
                            )
                        }

                        DataRequestState.Loading -> {
                            Column(
                                modifier = Modifier
                                    .padding(top = dimensionResource(id = R.dimen.deal_details_network_message_top_padding))
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator()
                                Text(
                                    text = stringResource(R.string.retrieving_details_progress_message),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        DataRequestState.Failure -> {
                            Column(
                                modifier = Modifier
                                    .padding(top = dimensionResource(id = R.dimen.deal_details_network_message_top_padding))
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.retrieving_details_error_message),
                                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.text_padding)),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center
                                )
                                Button(onClick = { onLoadDetailsPressed(deal) }) {
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
            }
        }
    }
}

/**
 * Composes the detail contents of the currently selected [Deal]
 */
@Composable
private fun DealDetailsContent(
    dealUiState: DealUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = AnnotatedString.fromHtml(
                htmlString = dealUiState.currentSelectedDeal?.description ?: ""
            ),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.deal_details_description_vertical_padding),
                )
        )
    }
}
