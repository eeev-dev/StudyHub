package com.example.studyhub.ui.screens.plans.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.data.local.entities.PlanEntity
import com.example.studyhub.ui.theme.sansFont
import com.example.studyhub.utils.formatDateRussian

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayItem(
    day: String,
    plans: List<PlanEntity>,
    navController: NavController
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(bottom = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(R.color.blue))
        ) {
            Text(
                text = day,
                fontFamily = sansFont,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 4.dp),
                color = Color.White
            )
        }
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            plans.forEach { plan ->
                HomeworkItem(plan) {
                    navController.navigate("update_screen/${plan.id}")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeworkItem(
    plan: PlanEntity,
    onItemClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onItemClick
            )
    ) {
        if (plan.isFinished) {
            Text(
                text = plan.content,
                fontFamily = sansFont,
                fontSize = 20.sp,
                textDecoration = TextDecoration.LineThrough,
                modifier = Modifier.padding(start = 5.dp),
                color = Color.Black
            )
            CustomText("✓ ${formatDateRussian(plan.deadline.toString())}")
        } else {
            CustomText(plan.content)
            CustomText("\uD83D\uDD52 ${formatDateRussian(plan.deadline.toString())}")
        }
        CustomText(plan.title)
    }
}

@Composable
fun CustomText(text: String) {
    Text(
        text = text,
        fontFamily = sansFont,
        fontSize = 20.sp,
        modifier = Modifier.padding(start = 5.dp),
        color = Color.Black
    )
}