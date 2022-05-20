package com.udobs.onetwotrip_testtask.ui.flights.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.udobs.onetwotrip_testtask.R
import com.udobs.onetwotrip_testtask.data.api.flights.ApiHelper
import com.udobs.onetwotrip_testtask.data.api.flights.RetrofitBuilder
import com.udobs.onetwotrip_testtask.ui.base.views.StyledTopAppBar
import com.udobs.onetwotrip_testtask.ui.flights.view.content.ErrorScreen
import com.udobs.onetwotrip_testtask.ui.flights.view.content.FlightsLazyColumn
import com.udobs.onetwotrip_testtask.ui.flights.view.content.LoadingScreen
import com.udobs.onetwotrip_testtask.ui.flights.view.content.NoConnectionErrorScreen
import com.udobs.onetwotrip_testtask.ui.flights.viewmodel.FlightsViewModel
import com.udobs.onetwotrip_testtask.ui.flights.viewmodel.ViewModelFactory
import com.udobs.onetwotrip_testtask.ui.theme.OneTwoTrip_TestTaskTheme
import com.udobs.onetwotrip_testtask.utils.Status

class FlightsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(FlightsViewModel::class.java)

        setContent {
            OneTwoTrip_TestTaskTheme { ActivityScreen(viewModel = viewModel) }
        }
    }

    override fun onBackPressed() {
        setResult(RESULT_OK, Intent())
        finish()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ActivityScreen(viewModel: FlightsViewModel) {
    val flights = viewModel.flightsToShow.observeAsState()
    val activity = (LocalContext.current as? FlightsActivity)
    Scaffold(
        topBar = {
            val flightsSize = flights.value?.data?.size ?: 0
            val flightsText = pluralStringResource(R.plurals.flight_plurals, count = flightsSize)
            val titleText = "$flightsSize $flightsText"
            StyledTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { activity?.onBackPressed() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                title = { Text(text = titleText) },
                actions = {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            )
        },
    ) { mainPadding ->
        Box(modifier = Modifier.padding(mainPadding)) {
            when (flights.value?.status) {
                Status.LOADING -> LoadingScreen()
                Status.SUCCESS -> {
                    FlightsLazyColumn(
                        flights = flights.value?.data!!,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                Status.ERROR -> NoConnectionErrorScreen(onRefreshAction = viewModel::getFlights)
                else -> ErrorScreen()
            }
        }
    }
}
