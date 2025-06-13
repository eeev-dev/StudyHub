package com.example.studyhub.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.studyhub.R
import com.example.studyhub.ui.navigation.NavShell
import com.example.studyhub.data.local.model.Lesson
import com.example.studyhub.ui.screens.schedule.components.DetailsCard
import com.example.studyhub.ui.screens.schedule.components.OfflineLesson
import com.example.studyhub.ui.screens.schedule.components.OnlineLesson
import com.example.studyhub.ui.screens.schedule.components.TabBar
import com.example.studyhub.data.local.model.schedule
import com.example.studyhub.data.local.model.subjects
import com.example.studyhub.utils.getToday
import com.example.studyhub.utils.shortWeekDays
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleScreen(
    navController: NavHostController
) {
    val context = LocalContext.current

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Black,
            darkIcons = false
        )
    }

    var detailsState by remember { mutableStateOf(false) }
    var currentDay by remember { mutableStateOf(getToday()) }

    val days = schedule.keys.toList()
    val pagerState = rememberPagerState(
        initialPage = days.indexOf(getToday()).coerceAtLeast(0),
        pageCount = { schedule.size })
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

    NavShell(navController, "Расписание") {
        Column {
            TabBar(
                shortWeekDays,
                pagerState
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
            ) { page ->

                currentDay = days[page]
                val lessons = schedule[currentDay].orEmpty().sortedBy { it.time.take(2).toInt() }

                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .padding(top = 6.dp)
                    ) {
                        lessons.forEach { lesson ->
                            val subjectList = subjects[lesson.title]
                            subjectList?.find { it.type.uppercase() == lesson.type.uppercase() }?.teacher?.let {
                                lesson.teacher = it
                            }
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