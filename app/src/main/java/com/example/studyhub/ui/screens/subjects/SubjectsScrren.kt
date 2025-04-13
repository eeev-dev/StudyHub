package com.example.studyhub.ui.screens.subjects

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DrawerValue
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
import androidx.navigation.NavController
import com.example.studyhub.ui.bars.Appbar
import com.example.studyhub.ui.bars.DrawerContent
import com.example.studyhub.ui.screens.schedule.schedule
import com.example.studyhub.utils.getToday
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SubjectsScreen(navController : NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val gesturesEnabled = drawerState.isOpen

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

        Scaffold(
            topBar = {
                Appbar("Дисциплины") {
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
                Column {
                    Row {

                    }
                }
            }
        }
    }
}
