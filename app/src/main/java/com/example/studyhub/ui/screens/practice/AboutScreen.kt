package com.example.studyhub.ui.screens.practice

import android.os.Build
import android.util.Log
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.ui.navigation.BottomDrawer
import com.example.studyhub.ui.screens.practice.components.SendConfirmation
import com.example.studyhub.ui.screens.practice.tabs.CompanyTab
import com.example.studyhub.ui.screens.practice.tabs.CompetitionTab
import com.example.studyhub.ui.screens.practice.tabs.ReviewTab
import com.example.studyhub.ui.screens.practice.tabs.competition.StudentItem
import com.example.studyhub.ui.theme.sansFont
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AboutScreen(
    navController: NavController,
    id: Int
) {

}

@Composable
fun Header(
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
            .background(colorResource(R.color.blue))
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
        ) {
            Text(
                text = title,
                fontFamily = sansFont,
                color = Color.White,
                fontSize = 36.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = onClick
                    ),
                imageVector = ImageVector.vectorResource(R.drawable.mail),
                contentDescription = "Заявка",
                tint = Color.White
            )
        }
    }
}