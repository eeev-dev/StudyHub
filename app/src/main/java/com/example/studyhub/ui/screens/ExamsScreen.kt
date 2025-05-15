package com.example.studyhub.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.ui.navigation.NavShell

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExamsScreen(navController: NavController) {
    NavShell(navController, "Экзамены", R.drawable.grid) {

    }
}