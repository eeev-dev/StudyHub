package com.example.studyhub.ui.screens

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.ui.components.Appbar
import com.example.studyhub.ui.components.DrawerContent
import com.example.studyhub.models.schedule
import com.example.studyhub.ui.components.NavShell
import com.example.studyhub.ui.screens.components.plans.DayItem
import com.example.studyhub.ui.screens.components.plans.ExpiredItem
import com.example.studyhub.ui.theme.sansFont
import com.example.studyhub.utils.getToday
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlansScreen(navController: NavController) {
    NavShell(navController, "Планы", R.drawable.filter) {
        LazyColumn {
            item {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(R.color.dark_blue)),
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