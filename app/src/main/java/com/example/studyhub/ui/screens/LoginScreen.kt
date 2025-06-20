package com.example.studyhub.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.ui.components.BlueRectangularButton
import com.example.studyhub.ui.theme.sansFont
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.studyhub.ui.screens.components.Message
import com.example.studyhub.viewmodels.login.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    BackHandler(enabled = true) { }
    val loginResult = viewModel.loginResult
    var studentId by remember { mutableStateOf("") }

    if (viewModel.isLogin) {
        navController.navigate("schedule_screen") {
            popUpTo(navController.currentDestination?.id ?: 0) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Message(viewModel.message) { viewModel.message = "" }
            Text("Вход", fontSize = 28.sp, fontWeight = FontWeight.Bold, fontFamily = sansFont)

            OutlinedTextField(
                value = studentId,
                onValueChange = {
                    studentId = it
                    viewModel.onStudentNumberChange(it)
                },
                label = { Text("Номер студенческого") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(R.color.blue),
                    unfocusedBorderColor = colorResource(R.color.inactive_tab),
                    focusedLabelColor = colorResource(R.color.blue),
                    unfocusedLabelColor = colorResource(R.color.inactive_tab),
                    cursorColor = colorResource(R.color.blue)
                )
            )

            BlueRectangularButton("Войти") {
                viewModel.login()
            }

            when (loginResult) {
                is LoginViewModel.LoginResult.Success -> {
                    LaunchedEffect(Unit) {
                        navController.navigate("schedule_screen") {
                            popUpTo(navController.currentDestination?.id ?: 0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                }

                is LoginViewModel.LoginResult.Error -> {
                    Text(
                        text = loginResult.message,
                        color = Color.Red
                    )
                    println(loginResult.message)
                }

                null -> {}
            }

        }
    }
}
