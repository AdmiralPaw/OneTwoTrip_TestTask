package com.udobs.onetwotrip_testtask.ui.flights.view.content

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udobs.onetwotrip_testtask.R
import com.udobs.onetwotrip_testtask.data.model.flights.Flight
import com.udobs.onetwotrip_testtask.utils.toPriceString

@Composable
fun FromToCardBoxWithTransfer(
    modifier: Modifier = Modifier,
    flight: Flight
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Column(
            modifier = Modifier.padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val priceAdditionalText = if (flight.prices.size > 1) "от " else ""
                val prettyPrice = "${flight.cheapestPrice.toPriceString()} ${flight.currency.value}"
                Text(
                    text = "$priceAdditionalText$prettyPrice",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                if (flight.withTransfer)
                    Transfer(count = flight.transferCount)
                else
                    NoTransfer()
            }
            FlightFromToCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                flight = flight,
            ) {
                flight.sortedPrices.forEach { price ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "${price.amount.toPriceString()} ${flight.currency.value}")
                        Text(text = price.type.value)
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Transfer(count: Int) {
    Row {
        Text(
            text = "$count ",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colors.secondary
        )
        Text(
            text = pluralStringResource(R.plurals.flight_transfer_plurals, count = count),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun NoTransfer() {
    Text(
        text = "без пересадок",
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colors.secondary
    )
}