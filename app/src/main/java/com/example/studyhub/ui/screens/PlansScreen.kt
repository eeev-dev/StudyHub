package com.example.studyhub.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.data.local.entities.PlanEntity
import com.example.studyhub.ui.navigation.NavShell
import com.example.studyhub.ui.screens.plans.components.DayItem
import com.example.studyhub.ui.screens.plans.components.ExpiredItem
import com.example.studyhub.utils.getWeekday
import com.example.studyhub.viewmodels.plans.PlansViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlansScreen(
    navController: NavController,
    viewModel: PlansViewModel = hiltViewModel()
) {
    var filterState by remember { mutableStateOf(true) }
    val plans by viewModel.plans.collectAsState()
    val dateFormat = remember { SimpleDateFormat("dd.MM.yyyy", Locale("ru")) }

    NavShell(navController, "Планы", appBarExtraIcon = {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.filter),
            contentDescription = stringResource(R.string.icon),
            Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        filterState = !filterState
                    }
                )

        )
    }, onExit = { isExit ->
        if (isExit) {
            navController.navigate("login_screen") {
                popUpTo(navController.currentDestination?.id ?: 0) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        } else {
            navController.navigate("settings_screen")
        }
    }) {
        Box(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = { navController.navigate("insert_screen") },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(70.dp)
                    .padding(end = 12.dp, bottom = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.blue)),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить",
                    tint = Color.White
                )
            }
        }
        if (filterState) {
            val today = remember {
                Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
            }

            val expiredPlans = plans
                .filter { plan ->
                    !plan.isFinished && plan.deadline != null && run {
                        val deadlineDate = dateFormat.parse(plan.deadline)
                        deadlineDate != null && deadlineDate.before(today.time)
                    }
                }
                .sortedBy { dateFormat.parse(it.deadline!!) }

            val upcomingPlans = plans
                .filter { plan ->
                    plan.deadline != null && run {
                        val deadlineDate = dateFormat.parse(plan.deadline)
                        deadlineDate != null && !deadlineDate.before(today.time)
                    }
                }
                .sortedBy { dateFormat.parse(it.deadline!!) }

            val plansByDate: Map<String, List<PlanEntity>> = upcomingPlans.groupBy { it.deadline!! }

            LazyColumn(Modifier.padding(horizontal = 12.dp)) {
                item {
                    if (!expiredPlans.isEmpty()) {
                        Spacer(Modifier.height(12.dp))
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.blue))
                        ) {
                            Column(
                                modifier = Modifier.padding(
                                    bottom = 12.dp,
                                    end = 12.dp,
                                    start = 12.dp
                                )
                            ) {
                                expiredPlans.forEach { plan ->
                                    Spacer(Modifier.height(12.dp))
                                    ExpiredItem(
                                        plan.content,
                                        { navController.navigate("update_screen/${plan.id}") },
                                        { viewModel.deletePlan(plan) })
                                }
                            }
                        }
                    }
                }
                item {
                    Spacer(Modifier.height(12.dp))
                }
                item {
                    Column {
                        for ((date, plans) in plansByDate) {
                            DayItem(getWeekday(date), plans, navController)
                        }
                    }
                }
            }
        } else {
            val sortedPlans = plans
                .filter { it.deadline != null }
                .sortedByDescending { dateFormat.parse(it.deadline!!) }

            val plansByDate: Map<String, List<PlanEntity>> = sortedPlans.groupBy { it.deadline!! }

            LazyColumn(Modifier.padding(horizontal = 12.dp)) {
                item {
                    Spacer(Modifier.height(12.dp))
                }
                for ((date, plans) in plansByDate) {
                    item {
                        DayItem(getWeekday(date), plans, navController)
                    }
                }
            }
        }
    }
}