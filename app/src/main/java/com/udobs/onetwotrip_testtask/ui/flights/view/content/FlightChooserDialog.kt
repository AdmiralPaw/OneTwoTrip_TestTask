package com.udobs.onetwotrip_testtask.ui.flights.view.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.udobs.onetwotrip_testtask.data.model.flights.FlightPrice
import com.udobs.onetwotrip_testtask.ui.base.views.BackDialogButton
import com.udobs.onetwotrip_testtask.ui.base.views.DialogButtonsRow
import com.udobs.onetwotrip_testtask.ui.base.views.NextDialogButton
import com.udobs.onetwotrip_testtask.utils.toPriceString
import java.util.*

@Composable
fun FlightChooserDialog(
    openDialog: MutableState<Boolean>,
    prices: List<FlightPrice>,
    currency: String,
    startActivityOnNextClick: (Int) -> Unit,
) {
    val selectedVariant = rememberSaveable { mutableStateOf(0) }
    AlertDialog(
        onDismissRequest = { openDialog.value = false },
        title = {
            Text(text = "Вы можете выбрать:", fontWeight = FontWeight.Medium, fontSize = 18.sp)
        },
        text = {
            Column {
                prices.forEachIndexed { i, price ->
                    DialogTypeAndPriceBox(
                        id = i,
                        price = price,
                        currency = currency,
                        selectedVariant = selectedVariant
                    )
                }
            }
        },
        buttons = {
            DialogButtonsRow(modifier = Modifier.fillMaxWidth()) {
                BackDialogButton { openDialog.value = false }
                NextDialogButton {
                    openDialog.value = false
                    startActivityOnNextClick(selectedVariant.value)
                }
            }
        }
    )
}

@Composable
fun DialogTypeAndPriceBox(
    id: Int,
    price: FlightPrice,
    currency: String,
    selectedVariant: MutableState<Int>
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val type = price.type.value.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        Checkbox(
            checked = selectedVariant.value == id,
            onCheckedChange = { selectedVariant.value = id })
        Text(
            text = "$type за ${price.amount.toPriceString()} $currency",
            fontSize = 16.sp,
            modifier = Modifier.clickable { selectedVariant.value = id }
        )
    }
}