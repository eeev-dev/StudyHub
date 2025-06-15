package com.example.studyhub.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.studyhub.ui.screens.ExamsScreen
import com.example.studyhub.ui.screens.LoginScreen
import com.example.studyhub.ui.screens.PlansScreen
import com.example.studyhub.ui.screens.PracticeScreen
import com.example.studyhub.ui.screens.PreviewScreen
import com.example.studyhub.ui.screens.ScheduleScreen
import com.example.studyhub.ui.screens.VKRScreen
import com.example.studyhub.ui.screens.plans.InsertPlanScreen
import com.example.studyhub.ui.screens.plans.UpdatePlanScreen
import com.example.studyhub.ui.screens.practice.AboutScreen
import com.example.studyhub.ui.screens.practice.LetterScreen
import com.example.studyhub.ui.screens.practice.SelectionScreen
import com.example.studyhub.ui.screens.schedule.components.SettingsScreen
import com.example.studyhub.ui.screens.vkr.SupervisorScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "preview_screen") {
        composable("preview_screen") { PreviewScreen(navController) }
        composable("login_screen") { LoginScreen(navController) }

        composable("plans_screen") { PlansScreen(navController) }
        composable("insert_screen") { InsertPlanScreen(navController) }
        composable(
            "update_screen/{planId}",
            arguments = listOf(navArgument("planId") { type = NavType.IntType })
        ) { backStackEntry ->
            val planId = backStackEntry.arguments?.getInt("planId") ?: 0
            UpdatePlanScreen(navController, planId)
        }

        composable("schedule_screen") { ScheduleScreen(navController) }
        composable("settings_screen") { SettingsScreen(navController) }

        composable("vkr_screen") { VKRScreen(navController) }
        composable("supervisor_screen") { SupervisorScreen(navController) }

        composable("practice_screen") { PracticeScreen(navController) }
        composable("selection_screen") { SelectionScreen(navController) }
        composable(
            "about_screen/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getInt("placeId") ?: 0
            AboutScreen(navController, placeId)
        }
        composable("letter_screen") { LetterScreen(navController) }

        composable("exams_screen") { ExamsScreen(navController) }
    }
}