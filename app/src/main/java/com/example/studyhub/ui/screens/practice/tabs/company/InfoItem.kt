package com.example.studyhub.ui.screens.practice.tabs.company

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyhub.R
import com.example.studyhub.ui.theme.sansFont

@Composable
fun InfoItem(
    iconId: Int,
    title: String,
    text: String
) {
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { isExpanded = !isExpanded }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            colorResource(R.color.blue),
                            CircleShape
                        )
                        .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(iconId),
                        contentDescription = "Иконка",
                        modifier = Modifier.align(Alignment.Center),
                        tint = Color.White
                    )
                }
                Text(
                    text = title,
                    fontFamily = sansFont,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 8.dp)
                )
            }
            AnimatedVisibility(
                visible = isExpanded && text.isNotEmpty(),
                enter = expandVertically(
                    animationSpec = tween(durationMillis = 300)
                ) + fadeIn(animationSpec = tween(300)),
                exit = shrinkVertically(
                    animationSpec = tween(durationMillis = 200)
                ) + fadeOut(animationSpec = tween(200))
            ) {
                Text(
                    text = text,
                    fontFamily = sansFont,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 15.dp, top = 8.dp)
                )
            }
        }
    }
}