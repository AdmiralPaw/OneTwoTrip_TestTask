package com.udobs.onetwotrip_testtask.ui.base.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DialogButtonsRow(
    modifier: Modifier = Modifier,
    buttons: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .padding(all = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        content = { buttons() }
    )
}

@Composable
fun BackDialogButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text("Назад", color = MaterialTheme.colors.secondary)
    }
}

@Composable
fun NextDialogButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text("Далее", color = MaterialTheme.colors.secondary)
    }
}
