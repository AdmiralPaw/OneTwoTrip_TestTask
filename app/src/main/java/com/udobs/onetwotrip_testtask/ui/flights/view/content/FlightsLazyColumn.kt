package com.udobs.onetwotrip_testtask.ui.flights.view.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.udobs.onetwotrip_testtask.data.model.flights.Flight

/**
 * Main flights search screen that display a flight cards
 */
@Composable
fun FlightsLazyColumn(
    modifier: Modifier = Modifier,
    flights: List<Flight>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
    ) {
        item { FlightsSearchDescription(modifier = Modifier.padding(top = 16.dp)) }
        items(flights) { FromToCardBoxWithTransfer(flight = it) }
        // A cheap equivalent to a spacer
        item { Box {} }
    }
}