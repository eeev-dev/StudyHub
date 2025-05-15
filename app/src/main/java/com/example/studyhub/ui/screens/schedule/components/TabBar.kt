package com.example.studyhub.ui.screens.schedule.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyhub.R
import com.example.studyhub.ui.theme.sansFont
import kotlinx.coroutines.launch

@Composable
fun TabBar(
    days: List<String>,
    pagerState: PagerState
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier.fillMaxWidth(),
        containerColor = colorResource(R.color.blue),
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                color = Color.White,
                height = 3.dp
            )
        }
    ) {
        val coroutineScope = rememberCoroutineScope()
        days.forEachIndexed { index, day ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = days[index],
                        fontFamily = sansFont,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            )
        }
    }
}
