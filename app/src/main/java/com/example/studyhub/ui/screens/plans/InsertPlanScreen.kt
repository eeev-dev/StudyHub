package com.example.studyhub.ui.screens.plans

import android.app.DatePickerDialog
import android.graphics.ColorFilter
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.data.local.entities.PlanEntity
import com.example.studyhub.viewmodels.plans.InsertViewModel
import java.util.Calendar

@Composable
fun InsertPlanScreen(
    navController: NavController,
    viewModel: InsertViewModel = hiltViewModel()
) {
    val blueColor = colorResource(id = R.color.blue)
    var title by rememberSaveable { mutableStateOf("") }
    var content by rememberSaveable { mutableStateOf("") }
    var deadline by rememberSaveable { mutableStateOf<String?>(null) }

    val calendar = remember { Calendar.getInstance() }
    val context = LocalContext.current

    // Для показа DatePickerDialog
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                deadline = String.format("%02d.%02d.%04d", dayOfMonth, month + 1, year)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Новый план", fontSize = 24.sp, color = blueColor, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Задание") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.blue),
                unfocusedBorderColor = colorResource(R.color.inactive_tab),
                focusedLabelColor = colorResource(R.color.blue),
                unfocusedLabelColor = colorResource(R.color.inactive_tab),
                cursorColor = colorResource(R.color.blue),
                unfocusedTrailingIconColor = colorResource(R.color.inactive_tab),
                focusedTrailingIconColor = colorResource(R.color.blue)
            )
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Предмет") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.blue),
                unfocusedBorderColor = colorResource(R.color.inactive_tab),
                focusedLabelColor = colorResource(R.color.blue),
                unfocusedLabelColor = colorResource(R.color.inactive_tab),
                cursorColor = colorResource(R.color.blue),
                unfocusedTrailingIconColor = colorResource(R.color.inactive_tab),
                focusedTrailingIconColor = colorResource(R.color.blue)
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = deadline ?: "Установите дедлайн",
                color = if (deadline != null) Color.Black else Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { datePickerDialog.show() },
                colors = ButtonDefaults.buttonColors(containerColor = blueColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Выбрать дату", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (title == "" || content == "" || deadline == null) Toast.makeText(context, "Нужно заполнить все поля", Toast.LENGTH_SHORT).show()
                else {
                    viewModel.addPlan(
                        PlanEntity(
                            title = title,
                            content = content,
                            deadline = deadline
                        )
                    )
                    navController.navigate("plans_screen") {
                        popUpTo(navController.currentDestination?.id ?: 0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = blueColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Сохранить", color = Color.White)
        }
    }
}
