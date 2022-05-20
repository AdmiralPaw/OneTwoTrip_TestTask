package com.udobs.onetwotrip_testtask.ui.flightsdelails.view.content

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.udobs.onetwotrip_testtask.data.model.flights.FlightTrip
import com.udobs.onetwotrip_testtask.ui.flights.view.content.AirportNameBox

@Composable
fun TripFromToCard(
    modifier: Modifier = Modifier,
    from: FlightTrip.Airport,
    to: FlightTrip.Airport,
    afterDividerContent: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.primaryVariant
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
            AirportNameBox(airport = from, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AirportNameBox(airport = to, modifier = Modifier.fillMaxWidth())
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
                    .height(1.dp),
                color = MaterialTheme.colors.primary
            )
            afterDividerContent()
        }
    }
}