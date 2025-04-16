package com.example.studyhub.ui.screens.components.subjects

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyhub.R
import com.example.studyhub.models.subjects
import com.example.studyhub.ui.theme.sansFont

@Composable
fun SubjectItem() {
    subjects.forEach { subject ->
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp)
        ) {
            Column(modifier = Modifier.animateContentSize()) {
                var subjectState by remember { mutableStateOf(false) }
                var teacherState by remember { mutableStateOf(false) }

                val transition = updateTransition(targetState = subjectState, label = "ExpandTransition")

                val rotation by transition.animateFloat(label = "ArrowRotation") { state ->
                    if (state) 90f else 0f
                }

                val alpha by transition.animateFloat(label = "ContentAlpha") { state ->
                    if (state) 1f else 0f
                }
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) { subjectState = !subjectState }
                ) {
                    Image(
                        bitmap = ImageBitmap.imageResource(R.drawable.arrow),
                        contentDescription = stringResource(R.string.icon),
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .align(Alignment.CenterVertically)
                            .graphicsLayer { rotationZ = rotation }
                    )
                    Text(
                        text = subject.key,
                        fontFamily = sansFont,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .weight(8f)
                            .align(Alignment.CenterVertically)
                            .padding(start = 8.dp),
                        color = Color.Black
                    )
                }
                if (subjectState || alpha > 0f) {
                    subject.value.forEach { info ->
                        Row(
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                                .alpha(alpha)
                        ) {
                            Text(
                                text = info.type,
                                fontFamily = sansFont,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 8.dp),
                                color = Color.Black
                            )
                            Box(
                                modifier = Modifier
                                    .weight(8f)
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 8.dp)
                            ) {
                                Text(
                                    text = info.teacher,
                                    fontFamily = sansFont,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(color = colorResource(R.color.dark_blue))
                                        .padding(horizontal = 20.dp, vertical = 5.dp)
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() },
                                        ) { teacherState = !teacherState },
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}