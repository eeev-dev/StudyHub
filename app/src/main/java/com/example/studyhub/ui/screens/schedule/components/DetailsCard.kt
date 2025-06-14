package com.example.studyhub.ui.screens.schedule.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import com.example.studyhub.ui.model.Lesson
import com.example.studyhub.ui.theme.sansFont
import com.example.studyhub.viewmodels.schedule.ScheduleViewModel

@Composable
fun DetailsCard(
    lesson: Lesson,
    viewModel: ScheduleViewModel,
    onClose: () -> Unit
) {
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
                        Row {
                            Text(
                                text = lesson.time,
                                fontFamily = sansFont,
                                fontSize = 16.sp,
                                color = Color.White,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = lesson.teacher,
                                fontFamily = sansFont,
                                fontSize = 16.sp,
                                color = Color.White,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                        }
                        Row {
                            val clipboardManager = LocalClipboardManager.current
                            Image(
                                bitmap = ImageBitmap.imageResource(R.drawable.copy),
                                contentDescription = stringResource(R.string.icon),
                                Modifier
                                    .size(36.dp)
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 8.dp)
                                    .clickable(
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() },
                                        onClick = { clipboardManager.setText(AnnotatedString(lesson.isOnline.second)) }
                                    ),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                            Text(
                                text = lesson.isOnline.second,
                                fontFamily = sansFont,
                                fontSize = 16.sp,
                                color = Color.White,
                                modifier =  Modifier.align(Alignment.CenterVertically).weight(1f)
                            )
                        }
                        Spacer(Modifier.height(6.dp))
                        Row {
                            var text by remember { mutableStateOf("") }
                            OutlinedTextField(
                                value = text,
                                onValueChange = { text = it },
                                label = { Text("Записать задание") },
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                trailingIcon = {
                                    IconButton(onClick = { }) {
                                        Icon(
                                            imageVector = Icons.Filled.AddCircle,
                                            contentDescription = "Вставить из буфера"
                                        )
                                    }
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.White,
                                    unfocusedBorderColor = Color.White,
                                    focusedLabelColor = Color.White,
                                    unfocusedLabelColor = colorResource(R.color.background_grey),
                                    cursorColor = Color.White,
                                    unfocusedTrailingIconColor = colorResource(R.color.background_grey),
                                    focusedTrailingIconColor = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}