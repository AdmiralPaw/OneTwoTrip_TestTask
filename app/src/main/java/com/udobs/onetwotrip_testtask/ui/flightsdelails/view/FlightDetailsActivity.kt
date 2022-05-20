package com.udobs.onetwotrip_testtask.ui.flightsdelails.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udobs.onetwotrip_testtask.data.model.flights.Flight
import com.udobs.onetwotrip_testtask.ui.base.views.StyledTopAppBar
import com.udobs.onetwotrip_testtask.ui.flights.view.content.ErrorScreen
import com.udobs.onetwotrip_testtask.ui.flightsdelails.view.content.FlightTripsLazyColumn
import com.udobs.onetwotrip_testtask.ui.theme.OneTwoTrip_TestTaskTheme
import com.udobs.onetwotrip_testtask.utils.toPriceString

class FlightDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val flight = intent.getSerializableExtra(FLIGHT_EXTRA) as Flight?
        val selectedVariant = intent.getIntExtra(SELECTED_PRICE, 0)

        setContent {
            OneTwoTrip_TestTaskTheme { ActivityScreen(flight, selectedVariant) }
        }
    }

    override fun onBackPressed() {
        setResult(RESULT_OK, Intent())
        finish()
    }

    companion object {
        const val FLIGHT_EXTRA = "FLIGHT_EXTRA"
        const val SELECTED_PRICE = "SELECTED_PRICE"
    }
}

@Composable
fun ActivityScreen(flight: Flight?, selectedPrice: Int) {
    val activity = (LocalContext.current as? FlightDetailsActivity)

    Scaffold(
        topBar = {
            StyledTopAppBar(
                title = {
                    Row {
                        Text(text = flight?.from?.townName ?: "Error")
                        Text(text = " → ")
                        Text(text = flight?.to?.townName ?: "Error")
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            activity?.onBackPressed()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
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
                    val prettyPrice =
                        flight?.sortedPrices?.get(selectedPrice)?.amount?.toPriceString() ?: "Error"
                    Text(
                        text = "Приобрести за $prettyPrice ${flight?.currency?.value}",
                        color = MaterialTheme.colors.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

            }
        }
    ) { mainPadding ->
        Box(modifier = Modifier.padding(mainPadding)) {
            when (flight) {
                null -> ErrorScreen()
                else -> FlightTripsLazyColumn(flight = flight, selectedPrice = selectedPrice)
            }
        }
    }
}