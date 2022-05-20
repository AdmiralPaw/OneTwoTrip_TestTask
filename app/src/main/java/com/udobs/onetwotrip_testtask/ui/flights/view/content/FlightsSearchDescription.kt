package com.udobs.onetwotrip_testtask.ui.flights.view.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udobs.onetwotrip_testtask.data.model.flights.FlightTrip

@Composable
fun FlightsSearchDescription(
    modifier: Modifier = Modifier,
    from: FlightTrip.Airport = FlightTrip.Airport.SVO,
    to: FlightTrip.Airport = FlightTrip.Airport.EWR,
) {
    Column(modifier = modifier) {
        // On task we have just one way trip, but it is possible to dev other cases
        Text(
            text = "В одну сторону",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Text(text = from.townName, fontSize = 12.sp)
            Text(text = " → ", fontSize = 12.sp)
            Text(text = to.townName, fontSize = 12.sp)
        }
    }
}