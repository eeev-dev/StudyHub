package com.example.studyhub.ui.screens.schedule

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studyhub.R
import com.example.studyhub.ui.components.Appbar
import com.example.studyhub.ui.components.DrawerContent
import com.example.studyhub.ui.components.NavShell
import com.example.studyhub.ui.screens.schedule.components.DetailsCard
import com.example.studyhub.ui.screens.schedule.components.OfflineLesson
import com.example.studyhub.ui.screens.schedule.components.OnlineLesson
import com.example.studyhub.ui.screens.schedule.components.TabBar
import com.example.studyhub.ui.theme.sansFont
import com.example.studyhub.utils.getToday
import com.example.studyhub.utils.shortWeekDays
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleScreen(
    navController: NavHostController
) {
    val context = LocalContext.current

    var detailsState by remember { mutableStateOf(false) }
    var currentLesson by remember {
        mutableStateOf(
            Lesson(
                "Математика",
                false to "2/628",
                "09:00",
                "лк"
            )
        )
    }
    var currentDay by remember { mutableStateOf(getToday()) }

    val days = schedule.keys.toList()
    val pagerState = rememberPagerState(
        initialPage = days.indexOf(getToday()).coerceAtLeast(0),
        pageCount = { schedule.size })

    val appBarExtraIcon = @Composable {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.grid),
            contentDescription = stringResource(R.string.icon),
            Modifier.fillMaxSize()
        )
    }

    NavShell(navController, "Расписание", appBarExtraIcon) {
        Column {
            TabBar(
                shortWeekDays,
                pagerState
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
            ) { page ->

                currentDay = days[page]
                val lessons = schedule[currentDay].orEmpty()

                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .padding(top = 6.dp)
                    ) {
                        lessons.forEach { lesson ->
                            val onClick = {
                                detailsState = true
                                currentLesson = lesson
                            }
                            if (lesson.isOnline.first) OnlineLesson(
                                time = lesson.time,
                                type = lesson.type,
                                title = lesson.title,
                                onClick = onClick
                            )
                            else OfflineLesson(
                                time = lesson.time,
                                type = lesson.type,
                                title = lesson.title,
                                onClick = onClick
                            )
                        }
                    }

                    if (detailsState) {
                        DetailsCard(currentLesson) { detailsState = false }
                    }
                }
            }
        }
    }
}