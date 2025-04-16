package com.example.studyhub.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.ui.components.NavShell
import com.example.studyhub.ui.components.SearchBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VKRScreen(navController: NavController) {
    NavShell(navController, "ВКР", R.drawable.filter) {
        var searchQuery by remember { mutableStateOf("") }

        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )
    }
}