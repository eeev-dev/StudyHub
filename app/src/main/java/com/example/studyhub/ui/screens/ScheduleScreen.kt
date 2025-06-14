package com.example.studyhub.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.studyhub.R
import com.example.studyhub.data.local.entities.ScheduleEntity
import com.example.studyhub.ui.navigation.NavShell
import com.example.studyhub.data.local.model.Lesson
import com.example.studyhub.data.local.model.convertSchedule
import com.example.studyhub.ui.screens.schedule.components.DetailsCard
import com.example.studyhub.ui.screens.schedule.components.OfflineLesson
import com.example.studyhub.ui.screens.schedule.components.OnlineLesson
import com.example.studyhub.ui.screens.schedule.components.TabBar
import com.example.studyhub.data.local.model.subjects
import com.example.studyhub.utils.getToday
import com.example.studyhub.utils.shortWeekDays
import com.example.studyhub.viewmodels.schedule.ScheduleViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleScreen(
    navController: NavHostController,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Black,
            darkIcons = false
        )
    }

    var term by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(viewModel.term) {
        term = viewModel.term
    }

    if (term != null) {
        viewModel.initSchedule()
    }

    val scheduleList by viewModel.schedule.collectAsState()

    if (scheduleList.isEmpty())  {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        var detailsState by remember { mutableStateOf(false) }

        // Сегодняшний день
        var currentDay by remember { mutableStateOf(getToday()) }

        val schedule = convertSchedule(scheduleList)

        val pagerState = rememberPagerState(
            initialPage = shortWeekDays.indexOf(getToday()).coerceAtLeast(0),
            pageCount = { shortWeekDays.size }
        )

        var currentLesson by remember {
            mutableStateOf(
                Lesson(
                    "Математика",
                    false to "2/628",
                    "10:00-11:20",
                    "лк"
                )
            )
        }

        NavShell(navController, "Расписание", onExit = {
            viewModel.logout()
            navController.navigate("login_screen") {
                popUpTo(navController.currentDestination?.id ?: 0) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }) {
            Column {
                TabBar(
                    shortWeekDays,
                    pagerState
                )
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->

                    currentDay = shortWeekDays[page]
                    val lessons = schedule[currentDay].orEmpty().sortedBy { it.time.take(2).toInt() }

                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .padding(top = 6.dp)
                        ) {
                            lessons.forEach { lesson ->
                                val onClick = {
                                    detailsState = true
                                    currentLesson = lesson
                                }
                                if (lesson.isOnline.first) OnlineLesson(lesson, onClick)
                                else OfflineLesson(lesson, onClick)
                            }
                        }

                        if (detailsState) {
                            DetailsCard(currentLesson) { detailsState = false }
                        }
                    }
                }
            }
        }
    }
}