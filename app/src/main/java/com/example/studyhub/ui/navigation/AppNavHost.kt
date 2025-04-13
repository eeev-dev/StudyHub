package com.example.studyhub.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.studyhub.ui.screens.schedule.ScheduleScreen
import com.example.studyhub.ui.screens.subjects.SubjectsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "schedule_screen") {
        composable("schedule_screen") { ScheduleScreen(navController) }
        composable("subjects_screen") { SubjectsScreen(navController) }
    }
}