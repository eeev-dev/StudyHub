package com.example.studyhub.ui.screens

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.ui.components.Appbar
import com.example.studyhub.ui.components.DrawerContent
import com.example.studyhub.ui.components.NavShell
import com.example.studyhub.ui.components.Spinner
import com.example.studyhub.ui.screens.components.subjects.SubjectItem
import kotlinx.coroutines.launch

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
