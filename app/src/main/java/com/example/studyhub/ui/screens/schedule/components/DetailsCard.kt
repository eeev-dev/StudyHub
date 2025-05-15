package com.example.studyhub.ui.screens.schedule.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.studyhub.R
import com.example.studyhub.data.local.model.Lesson
import com.example.studyhub.ui.theme.sansFont

@Composable
fun DetailsCard(lesson: Lesson, onClose: () -> Unit) {
    Dialog(onDismissRequest = onClose) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.blue)),
        ) {
            Box {
                Column {
                    Row {
                        Spacer(modifier = Modifier.weight(6f))
                        Image(
                            bitmap = ImageBitmap.imageResource(R.drawable.exit),
                            contentDescription = stringResource(R.string.icon),
                            Modifier
                                .weight(1f)
                                .size(36.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = onClose
                                ),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                    Column(modifier =  Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp)) {
                        Text(
                            text = lesson.time,
                            fontFamily = sansFont,
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier =  Modifier.padding(bottom = 6.dp)
                        )
                        Text(
                            text = lesson.teacher,
                            fontFamily = sansFont,
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier =  Modifier.padding(bottom = 6.dp)
                        )
                        Row {
                            val clipboardManager = LocalClipboardManager.current
                            var text by remember { mutableStateOf(lesson.isOnline.second) }
                            Image(
                                bitmap = ImageBitmap.imageResource(R.drawable.copy),
                                contentDescription = stringResource(R.string.icon),
                                Modifier
                                    .size(36.dp)
                                    .weight(1f)
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 8.dp)
                                    .clickable(
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() },
                                        onClick = { clipboardManager.setText(AnnotatedString(text)) }
                                    ),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                            OutlinedTextField(
                                value = text,
                                onValueChange = { text = it },
                                placeholder = { Text(text = if (lesson.isOnline.first) "Ссылка" else "Аудитория", color = Color.White) },
                                modifier = Modifier
                                    .weight(6f)
                                    .align(Alignment.CenterVertically),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.White,
                                    unfocusedIndicatorColor = Color.White,
                                    cursorColor = Color.White,
                                    focusedLabelColor = Color.White,
                                    unfocusedLabelColor = Color.White,
                                    unfocusedContainerColor = colorResource(R.color.blue),
                                    focusedContainerColor = colorResource(R.color.blue),
                                    unfocusedTextColor = Color.White,
                                    focusedTextColor = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}