package com.udobs.onetwotrip_testtask.ui.flights.view.content

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.udobs.onetwotrip_testtask.data.model.flights.Flight
import com.udobs.onetwotrip_testtask.ui.flightsdelails.view.FlightDetailsActivity

@Composable
fun FlightFromToCard(
    modifier: Modifier = Modifier,
    flight: Flight,
    afterDividerContent: @Composable () -> Unit
) {
    val openDialog = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val currency = flight.currency.value
    val from = flight.from
    val to = flight.to


    if (openDialog.value)
        FlightChooserDialog(
            openDialog = openDialog,
            prices = flight.sortedPrices,
            currency = currency
        ) { selectedPrice ->
            val intent = Intent(context, FlightDetailsActivity::class.java)
            intent.putExtra(FlightDetailsActivity.FLIGHT_EXTRA, flight)
            intent.putExtra(FlightDetailsActivity.SELECTED_PRICE, selectedPrice)
            context.startActivity(intent)
        }

    Card(
        modifier = modifier.clickable {
            if (flight.prices.size > 1) {
                openDialog.value = flight.prices.size > 1
            } else {
                val intent = Intent(context, FlightDetailsActivity::class.java)
                intent.putExtra(FlightDetailsActivity.FLIGHT_EXTRA, flight)
                context.startActivity(intent)
            }
        },
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