package com.udobs.onetwotrip_testtask.ui.flights.view.content

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udobs.onetwotrip_testtask.data.model.flights.FlightTrip

@Composable
fun AirportNameBox(
    modifier: Modifier = Modifier,
    airport: FlightTrip.Airport
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(text = airport.townName, fontWeight = FontWeight.Medium)
            Text(
                text = airport.value,
                fontSize = 12.sp,
                color = MaterialTheme.colors.secondaryVariant
            )
        }
        Box(
            modifier = Modifier.border(
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