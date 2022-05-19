@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class)

package com.udobs.onetwotrip_testtask.ui.flights.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.udobs.onetwotrip_testtask.R
import com.udobs.onetwotrip_testtask.data.api.flights.ApiHelper
import com.udobs.onetwotrip_testtask.data.api.flights.RetrofitBuilder
import com.udobs.onetwotrip_testtask.data.model.Flight
import com.udobs.onetwotrip_testtask.data.model.FlightPrice
import com.udobs.onetwotrip_testtask.data.model.FlightTrip
import com.udobs.onetwotrip_testtask.ui.flights.viewmodel.FlightsViewModel
import com.udobs.onetwotrip_testtask.ui.flights.viewmodel.ViewModelFactory
import com.udobs.onetwotrip_testtask.ui.flightsdelails.views.FlightDetailsActivity
import com.udobs.onetwotrip_testtask.ui.theme.OneTwoTrip_TestTaskTheme
import com.udobs.onetwotrip_testtask.utils.Status
import java.util.*

class FlightsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(FlightsViewModel::class.java)

        setContent {
            OneTwoTrip_TestTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    viewModel.getFlights()
                    Greeting(viewModel = viewModel)
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
fun AirportName(airport: FlightTrip.Airport) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(text = airport.townName, fontWeight = FontWeight.Medium)
            Text(
                text = airport.value,
                fontSize = 12.sp,
                color = MaterialTheme.colors.secondaryVariant
            )
        }
        Box(
            modifier = Modifier
                .border(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colors.secondaryVariant,
                    width = 1.dp
                )
        ) {
            Text(
                text = airport.name,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                fontSize = 14.sp,
                color = MaterialTheme.colors.secondaryVariant,
            )
        }
    }
}

@Composable
fun FromToCard(
    from: FlightTrip.Airport,
    to: FlightTrip.Airport,
    prices: List<FlightPrice>,
    currency: String,
    modifier: Modifier = Modifier,
    flight: Flight
) {
    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value)
        FlightChooserDialog(
            openDialog = openDialog,
            prices,
            currency,
            flight
        )
    val context = LocalContext.current

    Card(
        modifier = modifier.clickable {
            if (prices.size > 1) {
                openDialog.value = prices.size > 1
            } else {
                val intent = Intent(context, FlightDetailsActivity::class.java)
                intent.putExtra("TEST", flight)
                context.startActivity(intent)
            }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant
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
            prices.sortedBy { it.amount }.forEach { price ->
                val type = price.type.value.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "${price.amount.toPriceString()} $currency")
                    Text(text = type)
                }

            }
        }
    }
}

@Composable
fun FlightChooserDialog(
    openDialog: MutableState<Boolean>,
    prices: List<FlightPrice>,
    currency: String,
    flight: Flight
) {
    val selectedVariant = rememberSaveable { mutableStateOf(0) }
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(text = "Вы можете выбрать:", fontWeight = FontWeight.Medium, fontSize = 18.sp)
        },
        text = {
            Column {
                prices.sortedBy { it.amount }.forEachIndexed { i, price ->
                    PriceBox(
                        id = i,
                        type_ = price.type.value,
                        amount = price.amount,
                        currency = currency,
                        selectedVariant = selectedVariant
                    )
                }
            }
        },
        buttons = {
            val context = LocalContext.current
            Row(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = { openDialog.value = false },
                    modifier = Modifier
                ) {
                    Text("Назад", color = MaterialTheme.colors.secondary)
                }
                TextButton(
                    onClick = {
                        openDialog.value = false
                        val intent = Intent(context, FlightDetailsActivity::class.java)
                        intent.putExtra("TEST", flight)
                        intent.putExtra("TEST2", selectedVariant.value)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                ) {
                    Text("Далее", color = MaterialTheme.colors.secondary)
                }
            }
        }
    )
}

@Composable
fun PriceBox(
    id: Int,
    type_: String,
    amount: Int,
    currency: String,
    selectedVariant: MutableState<Int>
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val type = type_.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        Checkbox(
            checked = selectedVariant.value == id,
            onCheckedChange = { selectedVariant.value = id })
        Text(
            text = "$type за ${amount.toPriceString()} $currency",
            fontSize = 16.sp,
            modifier = Modifier.clickable { selectedVariant.value = id }
        )
    }
}

@Composable
fun FromToCardWithTransfer(flight: Flight) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ),
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
                Text(
                    text = "${
                        if (flight.prices.size > 1) "от " else ""
                    }${flight.cheapestPrice.toPriceString()} ${flight.currency.value}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                if (flight.withTransfer)
                    Transfer(count = flight.transferCount)
                else
                    NoTransfer()
            }
            FromToCard(
                from = flight.from,
                to = flight.to,
                prices = flight.prices,
                currency = flight.currency.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                flight = flight
            )
        }
    }
}

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


@Composable
fun Greeting(viewModel: FlightsViewModel) {
    val flights = viewModel.flightsToShow.observeAsState()
    val activity = (LocalContext.current as? FlightsActivity)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    flights.value?.data?.let {
                        Text(
                            text = "${it.size} ${
                                pluralStringResource(
                                    R.plurals.flight_plurals,
                                    count = it.size
                                )
                            }"
                        )
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
                actions = {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                contentColor = MaterialTheme.colors.primary,
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        },
    ) { mainPadding ->
        when (flights.value?.status) {
            Status.LOADING -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            Status.SUCCESS -> {
                flights.value?.data?.let {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier
                            .padding(mainPadding)
                            .padding(horizontal = 16.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Column {
                                Text(
                                    text = "В одну сторону",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row {
                                    Text(text = "Москва", fontSize = 12.sp)
                                    Text(text = " → ", fontSize = 12.sp)
                                    Text(text = "Нью-Йорк", fontSize = 12.sp)
                                }
                            }
                        }
                        items(it) {
                            FromToCardWithTransfer(
                                it
                            )
                        }
                        item {
                            Box {}
                        }
                    }
                }
            }
            Status.ERROR -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Возникли некоторые трудности")
                    Text(
                        text = "попробуйте снова",
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.clickable { viewModel.getFlights() })
                }
            }
            else -> {}
        }
    }
}
