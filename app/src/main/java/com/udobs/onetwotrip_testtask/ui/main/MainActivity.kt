package com.udobs.onetwotrip_testtask.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.udobs.onetwotrip_testtask.R
import com.udobs.onetwotrip_testtask.ui.flights.view.FlightsActivity
import com.udobs.onetwotrip_testtask.ui.theme.OneTwoTrip_TestTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OneTwoTrip_TestTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    // For starting new activity
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(painter = painterResource(id = R.drawable.onetwotrip_logo), contentDescription = null)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                context.startActivity(Intent(context, FlightsActivity::class.java))
            }
        ) {
            Text(text = "Из Москвы в Нью-Йорк!")
        }
    }
}