@file:OptIn(ExperimentalComposeUiApi::class)

package com.udobs.onetwotrip_testtask.ui.flightsdelails.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udobs.onetwotrip_testtask.data.model.Flight
import com.udobs.onetwotrip_testtask.data.model.FlightPrice
import com.udobs.onetwotrip_testtask.data.model.FlightTrip
import com.udobs.onetwotrip_testtask.ui.flights.views.AirportName
import com.udobs.onetwotrip_testtask.ui.theme.OneTwoTrip_TestTaskTheme
import java.util.*

class FlightDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val flight = intent.getSerializableExtra("TEST") as Flight?
        val selectedVariant = intent.getIntExtra("TEST2", 0)

        setContent {
            OneTwoTrip_TestTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting(flight, selectedVariant)
                }
            }
        }
    }

    override fun onBackPressed() {
        setResult(RESULT_OK, Intent())
        finish()
    }
}

@Composable
fun FromToCard(
    from: FlightTrip.Airport,
    to: FlightTrip.Airport,
    price: FlightPrice,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
            AirportName(airport = from)
            Spacer(modifier = Modifier.height(8.dp))
            AirportName(airport = to)
            Divider(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
                    .fillMaxWidth()
                    .height(1.dp),
                color = MaterialTheme.colors.primary
            )
            val type = price.type.value.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else {
                    it.toString()
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = type)
            }
        }
    }
}

@Composable
fun Greeting(flight_: Flight?, selectedVariant: Int) {
    val activity = (LocalContext.current as? FlightDetailsActivity)
    flight_?.let { flight ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row {
                            Text(text = flight.from.townName)
                            Text(text = " → ")
                            Text(text = flight.to.townName)
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                activity?.onBackPressed()
                            }
                        )
                        {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                    contentColor = MaterialTheme.colors.primary,
                    backgroundColor = MaterialTheme.colors.background,
                    elevation = 0.dp
                )
            },
            bottomBar = {
                BottomAppBar(
                    contentColor = Color.White,
                    backgroundColor = MaterialTheme.colors.secondary,
                    elevation = 0.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Приобрести за ${flight.prices.sortedBy { it.amount }[selectedVariant].amount.toPriceString()} ${flight.currency.value}",
                            color = MaterialTheme.colors.primary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                }
            }
        ) { mainPadding ->
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .padding(mainPadding)
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                val lastIndex = flight.trips.size - 1
                flight.trips.forEachIndexed { i, trip ->
                    item {
                        FromToCard(
                            from = trip.from,
                            to = trip.to,
                            price = flight.prices.sortedBy { it.amount }[selectedVariant],
                        )
                    }
                    if (i != lastIndex)
                        item { TransferDetails() }
                }
            }
        }
    }
}

@Composable
fun TransferDetails() {
    Text(
        text = "Пересадка с ожиданием...",
        color = MaterialTheme.colors.primary,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

private fun Int.toPriceString(): String {
    val x = this.toString().reversed()
    val s: StringBuilder = java.lang.StringBuilder("")
    x.mapIndexed { index, c ->
        if ((index) % 3 == 0) {
            s.append(" ")
        }
        s.append(c)
    }
    return s.toString().reversed().trim()
}