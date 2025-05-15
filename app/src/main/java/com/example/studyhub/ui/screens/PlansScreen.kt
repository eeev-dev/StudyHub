package com.example.studyhub.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.ui.navigation.NavShell
import com.example.studyhub.ui.screens.plans.components.DayItem
import com.example.studyhub.ui.screens.plans.components.ExpiredItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlansScreen(navController: NavController) {
    NavShell(navController, "Планы", R.drawable.filter) {
        LazyColumn {
            item {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(R.color.blue)),
                    modifier = Modifier.padding(15.dp)
                ) {
                    Column(modifier = Modifier.padding(top = 15.dp, start = 15.dp, end = 15.dp)) {
                        ExpiredItem("Презентация")
                        ExpiredItem("Лаб1")
                        ExpiredItem("2 семинар")
                    }
                }
            }
            item {
                Column(modifier = Modifier.padding(15.dp)) {
                    DayItem("Ср")
                    DayItem("Чт")
                }
            }
        }
    }
}