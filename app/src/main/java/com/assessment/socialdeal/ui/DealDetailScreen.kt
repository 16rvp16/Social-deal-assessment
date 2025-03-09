package com.assessment.socialdeal.ui

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.assessment.socialdeal.R
import com.assessment.socialdeal.model.Deal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DealDetailScreen(
    dealUiState: DealUiState,
    onBackPressed: () -> Unit,
    onDealFavoriteToggled: (Deal, Boolean) -> Unit,
    onLoadDetailsPressed: (Deal) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(topBar = {
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
                        contentDescription = stringResource(R.string.back_navigation)
                    )
                }
            }
        )

    }, modifier = modifier)
    { contentPadding ->

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
                        modifier = Modifier.padding(20.dp)
                    )

                    when (dealUiState.dealDetailsDataRequestState) {
                        DataRequestState.Success -> {
                            DealDetailsContent(
                                dealUiState = dealUiState, modifier = modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                            )
                        }

                        DataRequestState.Loading -> {
                            Column(
                                modifier = Modifier
                                    .padding(top = 24.dp)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator()
                                Text(
                                    text = "Retrieving details",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        DataRequestState.Failure -> {
                            Column(
                                modifier = Modifier
                                    .padding(top = 24.dp)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Something went wrong while retrieving details",
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center
                                )
                                Button(onClick = { onLoadDetailsPressed(deal) }) {
                                    Text(
                                        text = "Retry",
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
                    vertical = 12.dp,
                )
        )
    }
}
