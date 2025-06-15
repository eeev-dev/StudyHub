package com.example.studyhub.ui.screens.practice

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.ui.components.BlueCircularButton
import com.example.studyhub.ui.components.BlueRectangularButton
import com.example.studyhub.ui.components.ClipboardTextField
import com.example.studyhub.ui.navigation.NavShell
import com.example.studyhub.ui.screens.exam.DownloadFile
import com.example.studyhub.ui.screens.exam.Frame
import com.example.studyhub.ui.screens.practice.components.LoadFile
import com.example.studyhub.ui.screens.practice.tabs.company.ExpandableBox
import com.example.studyhub.utils.copyToDownloads
import com.example.studyhub.utils.openImage
import com.example.studyhub.viewmodels.practice.LetterViewModel
import kotlin.onSuccess

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LetterScreen(
    navController: NavController,
    viewModel: LetterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val result = viewModel.result
    var text by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(viewModel.text) {
        text = viewModel.text
    }

    LaunchedEffect(result) {
        result?.onSuccess {
            Toast.makeText(context, "Письмо отправлено", Toast.LENGTH_SHORT).show()
            navController.navigate("practice_screen") {
                popUpTo(navController.currentDestination?.id ?: 0) {
                    inclusive = true
                }
                launchSingleTop = true
            }
            viewModel.clearResult()
        }?.onFailure {
            Toast.makeText(context, "Ошибка: ${it.message}", Toast.LENGTH_SHORT).show()
            viewModel.clearResult()
        }
    }

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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            LazyColumn {
                item {
                    ExpandableBox(
                        R.drawable.flag,
                        "Инструкция",
                        "Обратитесь к ответственному сотруднику или руководителю практики на предприятиии. Предоставьте пример письма (см. ниже). Он поможет ответственному лицу оформить официальное письмо. Когда оно будет готово, отправьте название предприятия (название скопируйте из письма) и фото или скан письма через приложение. Поддерживаемые форматы: jpeg, jpg, png"
                    )
                }
                item {
                    Spacer(Modifier.height(12.dp))
                }
                item {
                    Frame(
                        title = "Пример письма",
                        onClick = {
                            openImage(context, copyToDownloads(context, "letter.png"))
                        }
                    ) { title, onClick ->
                        DownloadFile(title, onClick)
                    }
                }
                item {
                    Spacer(Modifier.height(12.dp))
                }
                item {
                    ClipboardTextField(
                        value = text,
                        label = "Официальное название предприятия",
                        onValueChange = { text = it }
                    )
                }
                item {
                    Spacer(Modifier.height(12.dp))
                }
                item {
                    LoadFile("Выбрать файл и отправить") { selectedFile ->
                        if (text == "") Toast.makeText(context, "Заполните поле", Toast.LENGTH_SHORT).show()
                        else viewModel.sendPlace(text, selectedFile)
                    }
                }
            }
        }
    }
}