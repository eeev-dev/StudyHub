package com.example.studyhub.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.studyhub.R

@Composable
fun SettingsMenu() {
    var menuExpanded by remember { mutableStateOf(false) }
    val iconSize = 36.dp

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
            .padding(end = 8.dp)
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.settings),
            contentDescription = stringResource(R.string.icon),
            modifier = Modifier
                .size(iconSize)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { menuExpanded = true }
                )
        )

        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Настройки") },
                onClick = {
                    menuExpanded = false
                    // обработка
                }
            )
            DropdownMenuItem(
                text = { Text("Выход") },
                onClick = {
                    menuExpanded = false
                    // обработка
                }
            )
        }
    }
}
