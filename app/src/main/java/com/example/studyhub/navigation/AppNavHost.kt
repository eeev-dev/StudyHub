package com.example.studyhub.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.studyhub.ui.screens.ExamsScreen
import com.example.studyhub.ui.screens.PlansScreen
import com.example.studyhub.ui.screens.PracticeScreen
import com.example.studyhub.ui.screens.ScheduleScreen
import com.example.studyhub.ui.screens.ScoreScreen
import com.example.studyhub.ui.screens.SubjectsScreen
import com.example.studyhub.ui.screens.VKRScreen
import com.example.studyhub.ui.screens.practice.AboutScreen
import com.example.studyhub.ui.screens.practice.LetterScreen
import com.example.studyhub.ui.screens.practice.ReportScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "vkr_screen") {
        composable("schedule_screen") { ScheduleScreen(navController) }
        composable("report_screen") { ReportScreen(navController) }
        composable("letter_screen") { LetterScreen(navController) }
        composable("subjects_screen") { SubjectsScreen(navController) }
        composable("homework_screen") { PlansScreen(navController) }
        composable("score_screen") { ScoreScreen(navController) }
        composable("practice_screen") { PracticeScreen(navController) }
        composable("about_screen") { AboutScreen(navController) }
        composable("vkr_screen") { VKRScreen(navController) }
        composable("exams_screen") { ExamsScreen(navController) }
    }
}