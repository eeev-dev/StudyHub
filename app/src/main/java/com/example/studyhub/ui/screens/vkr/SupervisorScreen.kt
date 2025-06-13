package com.example.studyhub.ui.screens.vkr

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studyhub.ui.navigation.NavShell
import com.example.studyhub.ui.screens.practice.components.SendConfirmation
import com.example.studyhub.ui.screens.vkr.components.Supervisor
import com.example.studyhub.viewmodels.vkr.SupervisorViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SupervisorScreen(
    navController: NavController,
    viewModel: SupervisorViewModel = hiltViewModel()
) {
    var dialogState by remember { mutableStateOf(false) }
    var currentTeacher by remember { mutableStateOf("") }

    val result = viewModel.postResult

    LaunchedEffect(result) {
        if (result == true) {
            navController.navigate("vkr_screen")
        }
    }

    if (dialogState) {
        SendConfirmation(
            onClose = { dialogState = false },
            onClick = {
                viewModel.postSupervisor(currentTeacher)
            }
        ) {
            Text(currentTeacher)
        }
    }

    val teachers by viewModel.teachers.collectAsState()

    NavShell(navController, "Преподаватели") {
        if (teachers.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else {
            print(teachers[0])
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                teachers.forEach { teacher ->
                    item {
                        Supervisor(
                            "Научный руководитель",
                            teacher,
                            isText = true
                        ) {
                            currentTeacher = teacher
                            dialogState = true
                        }
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}