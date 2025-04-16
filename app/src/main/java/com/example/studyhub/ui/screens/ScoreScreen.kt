package com.example.studyhub.ui.screens

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
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
import com.example.studyhub.models.schedule
import com.example.studyhub.ui.screens.components.scores.ScoreItem
import com.example.studyhub.models.subjects
import com.example.studyhub.ui.components.NavShell
import com.example.studyhub.utils.getToday
import kotlinx.coroutines.launch

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