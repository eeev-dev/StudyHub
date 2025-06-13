package com.example.studyhub.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyhub.R
import com.example.studyhub.ui.theme.sansFont

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Appbar(
    text: String,
    extraImageResource: Int,
    onExit: () -> Unit = {},
    onMenuClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.blue))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.menu),
                contentDescription = stringResource(R.string.icon),
                Modifier
                    .height(36.dp)
                    .width(36.dp)
                    .weight(1f)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = onMenuClick
                    )
            )
            Text(
                text = text,
                fontFamily = sansFont,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(4f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp),
                color = Color.White
            )
            Box(
                Modifier
                    .height(36.dp)
                    .width(36.dp)
                    .weight(1f)
            ) { AppBarExtraIcon(extraImageResource) }
            SettingsMenu(onExit)
        }
    }
}

@Composable
fun AppBarExtraIcon(image: Int) {
    if (image != 0) Image(
        bitmap = ImageBitmap.imageResource(image),
        contentDescription = stringResource(R.string.icon),
        Modifier.fillMaxSize()
    )
}