package com.example.studyhub.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.ui.navigation.NavShell
import com.example.studyhub.ui.screens.exam.DownloadFile
import com.example.studyhub.ui.screens.exam.Frame
import com.example.studyhub.viewmodels.exam.ExamViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExamsScreen(
    navController: NavController,
    viewModel: ExamViewModel = hiltViewModel()
) {
    NavShell(navController, "Экзамены", onExit = { isExit ->
        if (isExit) {
            viewModel.logout()
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Frame(
                title = "Расписание",
                onClick = { viewModel.downloadDocx() }
            ) { title, onClick ->
                DownloadFile(title, onClick)
            }
        }
    }
}