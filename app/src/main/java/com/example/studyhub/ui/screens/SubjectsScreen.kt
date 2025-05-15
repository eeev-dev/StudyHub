package com.example.studyhub.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.studyhub.ui.navigation.NavShell
import com.example.studyhub.ui.components.Spinner
import com.example.studyhub.ui.screens.subjects.components.SubjectItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SubjectsScreen(navController: NavController) {
    NavShell(navController, "Дисциплины") {
        Column {
            SubjectItem()
        }
        Spinner()
    }
}
