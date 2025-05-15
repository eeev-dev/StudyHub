package com.example.studyhub.ui.screens.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.ui.components.BlueRectangularButton
import com.example.studyhub.ui.components.IconLeft
import com.example.studyhub.ui.components.IconRight

@Composable
fun InfoScreen(
    navController: NavController
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp)
    ) {
        Column {
            IconRight(
                "Руководитель практики",
                "Исраилова Н.А.",
                R.drawable.teacher
            )
            Spacer(Modifier.height(12.dp))
            IconLeft(
                "Предприятие",
                "Айыл Банк",
                R.drawable.company
            )
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BlueRectangularButton("Отправить отчет") { navController.navigate("report_screen") }
        }
    }
}