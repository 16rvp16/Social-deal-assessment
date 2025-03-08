package com.assessment.socialdeal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import com.assessment.socialdeal.R
import com.assessment.socialdeal.model.Deal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DealDetailScreen(
    dealUiState: DealUiState,
    onBackPressed: () -> Unit,
    onDealFavoriteToggled: (Deal, Boolean) -> Unit,
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
                    DealHeader(
                        dealUiState = dealUiState,
                        deal = deal,
                        imageHasRoundedCorners = false,
                        onFavoriteToggled = { favorite -> onDealFavoriteToggled(deal, favorite) })
                    DealDetailsContent(
                        dealUiState = dealUiState, modifier = modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    )
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
