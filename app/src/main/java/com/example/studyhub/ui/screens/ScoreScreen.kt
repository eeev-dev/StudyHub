package com.example.studyhub.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.studyhub.ui.screens.scores.components.ScoreItem
import com.example.studyhub.data.local.model.subjects
import com.example.studyhub.ui.navigation.NavShell

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScoreScreen(navController: NavController) {
    NavShell(navController, "Успеваемость") {
        Column(modifier = Modifier.fillMaxSize()) {
            subjects.keys.forEach { key ->
                ScoreItem(key)
            }
        }
    }
}