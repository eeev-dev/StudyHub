package com.example.studyhub.ui.screens.practice

import android.os.Build
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.ui.components.BlueCircularButton
import com.example.studyhub.ui.components.BlueRectangularButton
import com.example.studyhub.ui.navigation.NavShell
import com.example.studyhub.ui.screens.practice.components.DownloadFile
import com.example.studyhub.ui.screens.practice.components.LoadFile

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LetterScreen(
    navController: NavController
) {
    NavShell(navController, "Практика") {
        Box(modifier = Modifier.fillMaxSize().padding(12.dp)) {
            Column {
                DownloadFile("Шаблон письма.docx") { }
                Spacer(Modifier.height(12.dp))
                LoadFile()
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                BlueRectangularButton("Отправить письмо") { }
            }
        }
    }
}