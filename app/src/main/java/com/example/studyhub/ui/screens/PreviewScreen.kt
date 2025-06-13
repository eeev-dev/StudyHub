package com.example.studyhub.ui.screens

import android.widget.Toast
import androidx.compose.runtime.Composable

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.viewmodels.PreviewViewModel
import kotlinx.coroutines.delay

@Composable
fun PreviewScreen(
    navController: NavController,
    viewModel: PreviewViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        while (viewModel.response == null) {
            viewModel.startServer()
            delay(15_000)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.isLogin()
    }

    if (viewModel.response != null) {
        if (viewModel.isLogin)
            navController.navigate("vkr_screen") {
                popUpTo(navController.currentDestination?.id ?: 0) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        else
            navController.navigate("login_screen") {
                popUpTo(navController.currentDestination?.id ?: 0) {
                    inclusive = true
                }
                launchSingleTop = true
            }
    }

    var progress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        val durationMillis = 60_000
        val intervalMillis = 100L
        val steps = (durationMillis / intervalMillis).toInt()
        repeat(steps) {
            delay(intervalMillis)
            progress = (it + 1).toFloat() / steps
        }
        if (viewModel.response == null) {
            if (viewModel.isLogin) {
                navController.navigate("plans_screen") {
                    popUpTo(navController.currentDestination?.id ?: 0) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            } else Toast.makeText(context, "Сервер недоступен, попробуйте зайти позже", Toast.LENGTH_SHORT).show()
        } else {
            if (viewModel.isLogin) {
                navController.navigate("vkr_screen") {
                    popUpTo(navController.currentDestination?.id ?: 0) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            } else {
                navController.navigate("login_screen") {
                    popUpTo(navController.currentDestination?.id ?: 0) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "StudyHub",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 45.sp
            )
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .height(8.dp),
                color = colorResource(R.color.blue),
                trackColor = colorResource(R.color.light_blue_track),
            )
        }
    }
}