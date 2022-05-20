package com.udobs.onetwotrip_testtask.ui.flightsdelails.view.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.udobs.onetwotrip_testtask.data.model.flights.Flight

@Composable
fun FlightTripsLazyColumn(
    modifier: Modifier = Modifier,
    flight: Flight,
    selectedPrice: Int
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier.padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        val lastIndex = flight.trips.size - 1
        flight.trips.forEachIndexed { i, trip ->
            item {
                TripFromToCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    from = trip.from,
                    to = trip.to
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = flight.sortedPrices[selectedPrice].type.value)
                    }
                }
            }
            if (i != lastIndex)
                item { TransferDetails() }
        }
    }
}