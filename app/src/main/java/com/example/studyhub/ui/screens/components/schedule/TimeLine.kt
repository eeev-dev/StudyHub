package com.example.studyhub.ui.screens.components.schedule

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyhub.R
import com.example.studyhub.models.Lesson
import com.example.studyhub.models.subjects
import com.example.studyhub.ui.theme.sansFont
import kotlin.collections.find

@Composable
fun VerticalLine(height: Int) {
    val circleColor = colorResource(R.color.dark_blue)
    val lineColor = colorResource(R.color.dark_blue)
    val circleSize = 12.dp
    val strokeWidth = 2.dp
    val density = LocalDensity.current
    val heightDp = with(density) { height.toDp() + 30.dp }
    Canvas(
        modifier = Modifier
            .height(heightDp)
            .width(strokeWidth)
    ) {
        val circleRadiusPx = circleSize.toPx() / 2
        val strokePx = strokeWidth.toPx()
        val centerX = size.width / 2
        val topCircleCenter = Offset(centerX, circleRadiusPx)
        val bottomCircleCenter = Offset(centerX, size.height - circleRadiusPx)

        drawLine(
            color = lineColor,
            start = topCircleCenter,
            end = bottomCircleCenter,
            strokeWidth = strokePx
        )

        drawCircle(
            color = circleColor,
            radius = circleRadiusPx,
            center = topCircleCenter
        )

        drawCircle(
            color = circleColor,
            radius = circleRadiusPx,
            center = bottomCircleCenter
        )
    }
}

@Composable
fun Dash() {
    val color = colorResource(R.color.dark_blue)
    Canvas(
        modifier = Modifier
            .width(10.dp)
            .height(2.dp)
    ) {
        drawLine(
            color = color,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = size.height
        )
    }
}

