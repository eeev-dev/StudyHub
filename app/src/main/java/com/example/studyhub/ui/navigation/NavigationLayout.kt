package com.example.studyhub.ui.navigation

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavShell(
    navController: NavController,
    appBarText: String,
    appBarExtraIcon: @Composable () -> Unit = {},
    onExit: (isExit:Boolean) -> Unit = {},
    content: @Composable () -> Unit,
) {
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
                    "plans_screen" -> navController.navigate(selected)
                    "exams_screen" -> navController.navigate(selected)
                    "practice_screen" -> navController.navigate(selected)
                    "vkr_screen" -> navController.navigate(selected)
                }
                scope.launch { drawerState.close() }
            }
        }
    ) {
        val scope = rememberCoroutineScope()

        BackHandler(enabled = drawerState.isOpen) {
            scope.launch {
                drawerState.close()
            }
        }

        Scaffold(
            topBar = {
                Appbar(appBarText, appBarExtraIcon, onExit = onExit) { scope.launch { drawerState.open() } }
            },
            content = { padding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(Color.White)
                ) {
                    content()
                }
            }
        )
    }
}