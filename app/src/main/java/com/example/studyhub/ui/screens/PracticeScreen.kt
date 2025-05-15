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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.ui.components.BlueCircularButton
import com.example.studyhub.ui.components.BlueRectangularButton
import com.example.studyhub.ui.components.Deadline
import com.example.studyhub.ui.components.IconLeft
import com.example.studyhub.ui.components.IconRight
import com.example.studyhub.ui.navigation.NavShell
import com.example.studyhub.ui.screens.practice.InfoScreen
import com.example.studyhub.ui.screens.practice.tabs.company.CompanyItem
import com.example.studyhub.ui.theme.sansFont

data class Practice(
    val company: String,
    val occupation: String,
    var places: Int
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PracticeScreen(navController: NavController) {
    val places = listOf(
        Practice("Айыл Банк", "Web-программирование", 4),
        Practice("Оптима Банк", "Мобильная разработка", 7),
        Practice("КатоЭкономикс", "1C-программирование", 1)
    )
    val deadline = "1 тур 12.03.24 12:00"
    NavShell(navController, "Практика") {
        if (deadline.isNotEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Column {
                    Deadline(deadline)
                    LazyColumn {
                        places.forEach { place ->
                            item {
                                CompanyItem(
                                    place.company,
                                    place.occupation,
                                    place.places
                                ) { navController.navigate("about_screen") }
                            }
                        }
                        item {
                            Spacer(Modifier.height(70.dp))
                        }
                    }
                }
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    BlueRectangularButton("Свое место практики") { navController.navigate("letter_screen") }
                }
            }
        } else {
            InfoScreen(navController)
        }
    }
}