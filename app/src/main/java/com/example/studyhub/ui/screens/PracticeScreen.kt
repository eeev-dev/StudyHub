package com.example.studyhub.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.ui.components.BlueCircularButton
import com.example.studyhub.ui.components.BlueRectangularButton
import com.example.studyhub.ui.components.ClipboardTextField
import com.example.studyhub.ui.components.Deadline
import com.example.studyhub.ui.components.IconLeft
import com.example.studyhub.ui.components.IconRight
import com.example.studyhub.ui.navigation.NavShell
import com.example.studyhub.ui.screens.components.Message
import com.example.studyhub.ui.screens.practice.components.SendConfirmation
import com.example.studyhub.ui.screens.practice.tabs.company.CompanyItem
import com.example.studyhub.ui.screens.vkr.components.Subject
import com.example.studyhub.ui.screens.vkr.components.Supervisor
import com.example.studyhub.ui.theme.sansFont
import com.example.studyhub.utils.convertGmtToLocal
import com.example.studyhub.viewmodels.practice.PracticeViewModel
import com.example.studyhub.viewmodels.vkr.VkrViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PracticeScreen(
    navController: NavController,
    viewModel: PracticeViewModel = hiltViewModel()
) {
    var text by rememberSaveable { mutableStateOf("") }
    var dialogState by remember { mutableStateOf(false) }

    if (dialogState) {
        SendConfirmation(
            onClose = { dialogState = false },
            onClick = {
                viewModel.saveTopic(text)
                viewModel.postTopic(text)
                dialogState = false
            }
        ) {
            ClipboardTextField(
                value = text,
                label = "Название темы",
                onValueChange = { text = it }
            )
        }
    }

    val isRefreshing = viewModel.isRefreshing.value

    NavShell(navController, "Диплом", onExit = {
        viewModel.logout()
        navController.navigate("login_screen") {
            popUpTo(navController.currentDestination?.id ?: 0) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.refresh() }
        ) {
            if (viewModel.graduate == null && viewModel.saved == null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(horizontal = 12.dp)
                ) {
                    LazyColumn(Modifier.fillMaxSize()) {
                        item {
                            Spacer(Modifier.height(12.dp))
                        }
                        item {
                            viewModel.saved?.let{ saved ->
                                Supervisor(
                                    "Научный руководитель",
                                    saved.supervisor,
                                    isText = true
                                )
                                Spacer(Modifier.height(17.dp))
                                Subject(
                                    "Тема",
                                    saved.topic,
                                    isText = true
                                )
                            }
                            viewModel.graduate?.let { graduate ->
                                graduate.message?.let {
                                    if (graduate.message != "") Message(graduate.message)
                                }
                                if (graduate.status == "Без заявки" || graduate.status == "Ожидает подтверждения") {
                                    Deadline(convertGmtToLocal(graduate.supervisor_deadline))
                                    Spacer(Modifier.height(17.dp))
                                }
                                if (graduate.status == "Подтвержден" || graduate.status == "Ожидает проверки") {
                                    Deadline(convertGmtToLocal(graduate.topic_deadline))
                                    Spacer(Modifier.height(17.dp))
                                }
                                if (graduate.status == "Без заявки") {
                                    Supervisor(
                                        "Научный руководитель",
                                        "Выбрать"
                                    ) { navController.navigate("supervisor_screen") }
                                } else {
                                    Supervisor(
                                        "Научный руководитель",
                                        graduate.supervisor ?: "",
                                        if (graduate.status == "Ожидает подтверждения" || graduate.status == "Выбор кафедры") graduate.status else null,
                                        true
                                    ) {
                                        if (graduate.status == "Ожидает подтверждения") navController.navigate(
                                            "supervisor_screen"
                                        )
                                    }
                                }
                                Spacer(Modifier.height(17.dp))
                                if (!(graduate.status == "Без заявки" || graduate.status == "Ожидает подтверждения" || graduate.status == "Выбор кафедры")) {
                                    if (graduate.status == "Подтвержден") {
                                        Subject(
                                            "Тема",
                                            "Отправить"
                                        ) { dialogState = true }
                                    } else {
                                        Subject(
                                            "Тема",
                                            graduate.topic ?: "",
                                            if (graduate.status == "Ожидает проверки") graduate.status else null,
                                            true
                                        ) {
                                            if (graduate.status == "Ожидает проверки") dialogState =
                                                true
                                        }
                                    }
                                }
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