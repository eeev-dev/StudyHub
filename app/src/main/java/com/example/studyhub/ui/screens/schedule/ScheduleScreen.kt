package com.example.studyhub.ui.screens.schedule

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.studyhub.ui.bars.Appbar
import com.example.studyhub.ui.bars.DrawerContent
import com.example.studyhub.ui.screens.schedule.components.LessonItem
import com.example.studyhub.utils.getToday
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleScreen(
    navController: NavHostController
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val gesturesEnabled = drawerState.isOpen
    val days = schedule.keys.toList()
    val pagerState = rememberPagerState(initialPage = days.indexOf(getToday()), pageCount = { schedule.size })
    var dayState by remember { mutableStateOf(getToday()) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = gesturesEnabled,
        drawerContent = {
            DrawerContent { selected ->
                when (selected) {
                    "schedule_screen" -> navController.navigate(selected)
                    "subjects_screen" -> navController.navigate(selected)
                }
                scope.launch { drawerState.close() }
            }
        }
    ) {
        BackHandler {
            if (drawerState.isOpen) {
                scope.launch { drawerState.close() }
            }
        }

        LaunchedEffect(pagerState.currentPage) {
            dayState = days[pagerState.currentPage]
        }

        Scaffold(
            topBar = {
                Appbar(dayState) {
                    scope.launch { drawerState.open() }
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color.White)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->

                    val day = days[page]
                    val lessons = schedule[day].orEmpty()

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        lessons.forEach { lesson ->
                            LessonItem(lesson.time, lesson.title, lesson.type)
                        }
                    }
                }
            }
        }
    }
}