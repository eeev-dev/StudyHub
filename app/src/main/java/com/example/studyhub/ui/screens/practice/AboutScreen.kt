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
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.ui.components.BlueRectangularButton
import com.example.studyhub.ui.navigation.BottomDrawer
import com.example.studyhub.ui.screens.practice.components.SendConfirmation
import com.example.studyhub.ui.screens.practice.tabs.CompanyTab
import com.example.studyhub.ui.screens.practice.tabs.ReviewTab
import com.example.studyhub.ui.theme.sansFont
import com.example.studyhub.viewmodels.practice.AboutViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AboutScreen(
    navController: NavController,
    id: Int,
    viewModel: AboutViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val icons = listOf(
        Pair(R.drawable.info, "Информация"),
        Pair(R.drawable.star, "Отзывы")
    )
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { icons.size }
    )
    var dialogState by remember { mutableStateOf(false) }

    viewModel.getPlace(id)
    val place = viewModel.place

    val reviews by viewModel.reviews.collectAsState()

    LaunchedEffect(place?.id) {
        place?.let {
            viewModel.getReviews(it.id)
        }
    }

    val result = viewModel.postResult

    LaunchedEffect(result) {
        if (result == true) {
            navController.navigate("practice_screen")
        }
        if (result == false) {
            navController.navigate("practice_screen")
        }
    }

    Box(Modifier.fillMaxSize()) {
        if (place != null) {
            Box(modifier = Modifier.background(Color.White)) {
                if (dialogState) {
                    SendConfirmation(
                        textButton = "Отправить",
                        onClose = { dialogState = false },
                        onClick = {
                            viewModel.sendPlace(place.id)
                            dialogState = false
                        },
                        content = {
                            Column {
                                Text(
                                    text = "Вы уверены?",
                                    fontSize = 20.sp,
                                    fontFamily = sansFont
                                )
                            }
                        }
                    )
                }
                Column(modifier = Modifier.align(Alignment.TopStart)) {
                    Header(place.title.toString()) {
                        dialogState = !dialogState
                    }
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxSize()
                    ) { page ->
                        when (page) {
                            0 -> CompanyTab(place)
                            1 -> ReviewTab(reviews)
                            else -> CompanyTab(place)
                        }
                    }
                }
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    BottomDrawer(icons, pagerState)
                }
            }
        } else {
            Box(Modifier.align(Alignment.Center)) { CircularProgressIndicator() }
        }
    }
}

@Composable
fun Header(
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .defaultMinSize(minHeight = 180.dp)
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
                modifier = Modifier.align(Alignment.CenterVertically).weight(1f)
            )
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