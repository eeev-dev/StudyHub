package com.example.studyhub.ui.screens.schedule.components

import android.content.Intent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyhub.R
import com.example.studyhub.ui.theme.sansFont
import androidx.core.net.toUri
import com.example.studyhub.data.local.model.Lesson

@Composable
fun OnlineLesson(
    lesson: Lesson,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 6.dp, horizontal = 8.dp)
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.desktop),
            contentDescription = stringResource(R.string.icon),
            Modifier
                .weight(1f)
                .aspectRatio(1f)
                .padding(1.dp)
                .align(Alignment.CenterVertically)
        )
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.blue)),
            modifier = Modifier
                .weight(5f)
                .padding(start = 8.dp)
        ) {
            var scale by remember { mutableFloatStateOf(1f) }
            val animatedScale by animateFloatAsState(
                targetValue = scale,
                animationSpec = tween(durationMillis = 200),
                label = "scaleAnim"
            )
            val context = LocalContext.current
            var onPressPermission by remember { mutableStateOf(true) }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(12.dp)
                    .graphicsLayer {
                        scaleX = animatedScale
                        scaleY = animatedScale
                    }
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                onPressPermission = false
                                val intent = Intent(Intent.ACTION_VIEW, lesson.isOnline.second.toUri())
                                context.startActivity(intent)
                            },
                            onPress = {
                                try {
                                    scale = 0.95f
                                    awaitRelease()
                                    if (onPressPermission) onClick()
                                    onPressPermission = true
                                } finally {
                                    scale = 1f
                                }
                            }
                        )
                    }
            ) {
                Text(
                    text = lesson.time.take(5),
                    fontFamily = sansFont,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(start = 5.dp),
                    color = Color.White
                )
                Text(
                    text = lesson.title,
                    fontFamily = sansFont,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .weight(2f)
                        .align(Alignment.CenterVertically)
                        .padding(start = 5.dp),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = lesson.type,
                    fontFamily = sansFont,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(start = 5.dp),
                    color = Color.White
                )
            }
        }
    }
}