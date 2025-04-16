package com.example.studyhub.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyhub.R
import com.example.studyhub.ui.theme.sansFont

@Composable
fun Spinner() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .animateContentSize()
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .align(Alignment.BottomCenter)
        ) {
            var spinnerState by remember { mutableStateOf(false) }

            val transition =
                updateTransition(targetState = spinnerState, label = "ExpandTransition")

            val rotation by transition.animateFloat(label = "ArrowRotation") { state ->
                if (state) 90f else 270f
            }

            val alpha by transition.animateFloat(label = "ContentAlpha") { state ->
                if (state) 1f else 0f
            }

            if (spinnerState || alpha > 0f) {
                for (i in 1..6) {
                    Row(modifier = Modifier.alpha(alpha)) {
                        Text(
                            text = "Семестр $i",
                            fontFamily = sansFont,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .weight(8f)
                                .align(Alignment.CenterVertically)
                                .padding(8.dp),
                            color = Color.Black
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colorResource(R.color.dark_blue))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .height(IntrinsicSize.Min)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) { spinnerState = !spinnerState }
                ) {
                    Text(
                        text = "Семестр 7",
                        fontFamily = sansFont,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .weight(12f)
                            .align(Alignment.CenterVertically),
                        color = Color.White
                    )
                    Image(
                        painter = painterResource(R.drawable.arrow),
                        contentDescription = stringResource(R.string.icon),
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .align(Alignment.CenterVertically)
                            .graphicsLayer { rotationZ = rotation }
                    )
                }
            }

        }
    }
}