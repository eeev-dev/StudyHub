package com.example.studyhub.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studyhub.R
import com.example.studyhub.ui.components.NavShell
import com.example.studyhub.models.Lesson
import com.example.studyhub.ui.screens.components.schedule.DetailsCard
import com.example.studyhub.ui.screens.components.schedule.OfflineLesson
import com.example.studyhub.ui.screens.components.schedule.OnlineLesson
import com.example.studyhub.ui.screens.components.schedule.TabBar
import com.example.studyhub.models.schedule
import com.example.studyhub.models.subjects
import com.example.studyhub.ui.screens.components.schedule.Dash
import com.example.studyhub.ui.screens.components.schedule.VerticalLine
import com.example.studyhub.ui.theme.sansFont
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

    NavShell(navController, "Расписание", R.drawable.grid) {
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
                val lessons = schedule[currentDay].orEmpty()

                val times = listOf("10:00", "11:30", "13:00", "14:30", "16:00", "17:30")

                Box(modifier = Modifier.fillMaxSize()) {
                    Row(modifier = Modifier.padding(vertical = 15.dp, horizontal = 15.dp)) {
                        var columnHeight by remember { mutableIntStateOf(0) }
                        VerticalLine(columnHeight)
                        Column(
                            modifier = Modifier.padding(top = 30.dp)
                                .onGloballyPositioned { coordinates ->
                                    columnHeight = coordinates.size.height
                                }
                        ) {
                            times.forEach { time ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(bottom = 15.dp).height(70.dp)
                                ) {
                                    Dash()
                                    Text(
                                        text = time,
                                        fontFamily = sansFont,
                                        fontSize = 16.sp,
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                            .padding(start = 5.dp),
                                        color = colorResource(R.color.dark_blue)
                                    )
                                    lessons.find { it.time.take(5) == time }?.let {
                                        val subjectList = subjects[it.title]
                                        subjectList?.find { it.type.uppercase() == it.type.uppercase() }?.teacher?.let { teacher ->
                                            it.teacher = teacher
                                        }
                                        val onClick = {
                                            detailsState = true
                                            currentLesson = it
                                        }
                                        if (it.isOnline.first) OnlineLesson(it, onClick)
                                        else OfflineLesson(it, onClick)
                                    }
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
}