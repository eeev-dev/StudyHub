package com.example.studyhub.ui.screens.practice.tabs.company

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyhub.R
import com.example.studyhub.ui.components.BlueRectangularButton
import com.example.studyhub.ui.theme.sansFont

@Composable
fun CompanyItem(
    title: String,
    occupation: String,
    places: Int,
    navigate: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.padding(vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                fontFamily = sansFont,
                fontSize = 32.sp
            )
            Text(
                text = occupation,
                fontFamily = sansFont,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Мест: $places",
                fontFamily = sansFont,
                fontSize = 20.sp,
                color = colorResource(R.color.grey_for_text),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            BlueRectangularButton("Подробности") { navigate() }
        }
    }
}