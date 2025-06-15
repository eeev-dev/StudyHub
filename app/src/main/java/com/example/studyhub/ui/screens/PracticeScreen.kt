package com.example.studyhub.ui.screens

import android.os.Build
import android.widget.Toast
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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
import com.example.studyhub.ui.screens.practice.tabs.review.RatingBar
import com.example.studyhub.ui.screens.vkr.components.Subject
import com.example.studyhub.ui.screens.vkr.components.Supervisor
import com.example.studyhub.ui.theme.sansFont
import com.example.studyhub.utils.convertGmtToLocal
import com.example.studyhub.utils.copyToDownloads
import com.example.studyhub.utils.openFile
import com.example.studyhub.viewmodels.practice.PracticeViewModel
import com.example.studyhub.viewmodels.vkr.VkrViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PracticeScreen(
    navController: NavController,
    viewModel: PracticeViewModel = hiltViewModel()
) {
    var dialogState by remember { mutableStateOf(false) }
    val context = LocalContext.current

    var text by remember { mutableStateOf("") }

    if (dialogState) {
        var rating by remember { mutableIntStateOf(0) }
        SendConfirmation(
            onClose = { dialogState = false },
            onClick = {
                if (rating == 0 || text.isEmpty()) Toast.makeText(
                    context,
                    "Нужно заполнить все",
                    Toast.LENGTH_SHORT
                ).show()
                else if (text.length >= 1000) Toast.makeText(
                    context,
                    "Отзыв должен быть не длиннее 1000 символов",
                    Toast.LENGTH_SHORT
                ).show()
                else {
                    viewModel.intern?.let { intern ->
                        if (intern.place_id != null) {
                            viewModel.sendReview(
                                rating,
                                text,
                                LocalDate.now().toString(),
                                intern.place_id
                            )
                        }
                    }
                    dialogState = false
                }
            },
            content = {
                Column {
                    Column(
                        modifier = Modifier.weight(1f, fill = false)
                    ) {
                        RatingBar(
                            rating,
                            onRatingChanged = { newRating ->
                                rating = newRating
                            }
                        )

                        ClipboardTextField(
                            value = text,
                            label = "Вставьте отзыв",
                            onValueChange = { text = it }
                        )

                        Spacer(Modifier.height(12.dp))
                    }
                }
            })
    }

    val isRefreshing = viewModel.isRefreshing.value

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
            if (viewModel.intern == null) {
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
                    viewModel.intern?.let { intern ->
                        val title = if (intern.place == "Свое место практики") viewModel.title else intern.place
                        LazyColumn(Modifier.fillMaxSize()) {
                            item {
                                Spacer(Modifier.height(12.dp))
                            }
                            item {
                                intern.message?.let {
                                    if (intern.message != "") Message(intern.message)
                                }
                                if (intern.status == "Без заявки" || intern.status == "Ожидает подтверждения") {
                                    Deadline(convertGmtToLocal(intern.deadline))
                                    Spacer(Modifier.height(17.dp))
                                }
                                Supervisor(
                                    "Руководитель практики",
                                    intern.head_teacher,
                                    isText = true
                                )
                                Spacer(Modifier.height(17.dp))
                                when (intern.status) {
                                    "Подтвержден" -> Subject(
                                        "Предприятие",
                                        title,
                                        isText = true
                                    )

                                    "Ожидает подтверждения" -> Subject(
                                        "Предприятие",
                                        title,
                                        intern.status,
                                        true
                                    ) { navController.navigate("selection_screen") }

                                    "Без заявки" -> Subject(
                                        "Предприятие",
                                        "Выбрать"
                                    ) { navController.navigate("selection_screen") }

                                    "Выбор кафедры" -> Subject(
                                        "Предприятие",
                                        "",
                                        intern.status,
                                        true
                                    )
                                }
                            }
                            item {
                                Spacer(Modifier.height(12.dp))
                            }
                        }
                        if (intern.status != "Выбор кафедры") {
                            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                                Column {
                                    if (intern.status != "Подтвержден") {
                                        BlueRectangularButton("Свое место практики") {
                                            navController.navigate("letter_screen")
                                        }
                                    }
                                    Spacer(Modifier.height(12.dp))
                                    if (intern.status == "Подтвержден") {
                                        if (intern.place != "Свое место практики") {
                                            BlueRectangularButton("Оставить отзыв") {
                                                dialogState = true
                                            }
                                        }
                                        Spacer(Modifier.height(12.dp))
                                        BlueRectangularButton("Шаблон недельного отчета") {
                                            openFile(
                                                context,
                                                copyToDownloads(
                                                    context,
                                                    "Шаблон отчета.docx"
                                                )
                                            )
                                        }
                                        Spacer(Modifier.height(12.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}