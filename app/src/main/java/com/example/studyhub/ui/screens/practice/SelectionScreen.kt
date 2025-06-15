package com.example.studyhub.ui.screens.practice

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ModalDrawer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studyhub.ui.components.Deadline
import com.example.studyhub.ui.navigation.NavShell
import com.example.studyhub.ui.screens.components.Message
import com.example.studyhub.ui.screens.practice.tabs.company.CompanyItem
import com.example.studyhub.utils.convertGmtToLocal
import com.example.studyhub.viewmodels.practice.SelectionViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectionScreen(
    navController: NavController,
    viewModel: SelectionViewModel = hiltViewModel()
) {
    val places by viewModel.places.collectAsState()

    val isRefreshing by viewModel.isRefreshing

    NavShell(navController, "Практика", onExit = { isExit ->
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
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.refresh() }
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (viewModel.intern == null) {
                    CircularProgressIndicator()
                } else {
                    viewModel.intern?.let { intern ->
                        LazyColumn(modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp)) {
                            item {
                                Spacer(Modifier.height(12.dp))
                            }
                            item {
                                if (intern.status == "Без заявки" || intern.status == "Ожидает подтверждения") {
                                    Deadline(convertGmtToLocal(intern.deadline))
                                    Spacer(Modifier.height(8.dp))
                                }
                            }
                            item {
                                intern.message?.let { if (intern.message != "") Message(intern.message.toString()) }
                            }
                            places.forEach { place ->
                                item {
                                    CompanyItem(place) { navController.navigate("about_screen/${place.id}") }
                                }
                            }
                            item {
                                Spacer(Modifier.height(12.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}