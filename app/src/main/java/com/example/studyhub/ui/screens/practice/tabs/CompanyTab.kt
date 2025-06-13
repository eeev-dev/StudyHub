package com.example.studyhub.ui.screens.practice.tabs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyhub.R
import com.example.studyhub.data.remote.models.Place
import com.example.studyhub.ui.screens.practice.tabs.company.InfoItem
import com.example.studyhub.ui.theme.sansFont

@Composable
fun CompanyTab(place: Place) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = 12.dp, end = 12.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(6.dp).background(Color.Transparent))
        }
        item {
            InfoItem(
                R.drawable.user_check,
                "Требования",
                place.requirements
            )
        }
        item {
            InfoItem(
                R.drawable.zap,
                "Перспективы",
                place.outlook
            )
        }
        item {
            InfoItem(
                R.drawable.phone,
                "Контакты",
                place.contacts
            )
        }
        item {
            Spacer(modifier = Modifier.height(90.dp).background(Color.Transparent))
        }
    }
}