package com.udobs.onetwotrip_testtask.ui.flights.view.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Screen, that help user refresh a list, when connection back
 */
@Composable
fun NoConnectionErrorScreen(
    modifier: Modifier = Modifier,
    onRefreshAction: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Возникли некоторые трудности")
        Text(
            text = "попробуйте снова",
            color = MaterialTheme.colors.secondary,
            modifier = Modifier.clickable { onRefreshAction() })
    }
}