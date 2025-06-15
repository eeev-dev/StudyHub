package com.example.studyhub.ui.navigation

import com.example.studyhub.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.studyhub.ui.theme.sansFont

@Composable
fun DrawerContent(
    onItemSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 80.dp)
            .background(colorResource(R.color.blue))
    ) {
        Spacer(modifier = Modifier.height(20.dp).fillMaxWidth())

        DrawerItem("Расписание", Icons.Default.DateRange) { onItemSelected("schedule_screen") }
        DrawerItem("Планы", Icons.Default.Home) { onItemSelected("plans_screen") }
        DrawerItem("Экзамены", Icons.Default.Done) { onItemSelected("exams_screen") }
        DrawerItem("Практика", Icons.Default.Email) { onItemSelected("practice_screen") }
        DrawerItem("ВКР", Icons.Default.List) { onItemSelected("vkr_screen") }
    }
}

@Composable
fun DrawerItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp), tint = Color.White)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, fontFamily = sansFont, color = Color.White)
    }
}
