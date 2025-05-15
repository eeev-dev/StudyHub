package com.example.studyhub.ui.screens

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.data.remote.ApiClient
import com.example.studyhub.data.remote.models.PostRequest
import com.example.studyhub.ui.navigation.NavShell
import com.example.studyhub.ui.components.SearchBar
import com.example.studyhub.ui.navigation.BottomDrawer
import com.example.studyhub.ui.screens.vkr.components.PostForm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VKRScreen(navController: NavController) {
    val icons = listOf(
        Pair(R.drawable.info, "Информация"),
        Pair(R.drawable.users, "Преподаватели"),
        Pair(R.drawable.search, "Поиск")
    )
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { icons.size }
    )
    NavShell(navController, "ВКР", R.drawable.filter) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                var searchQuery by remember { mutableStateOf("") }
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it }
                )
                PostForm { teacher, subject, student ->
                    val post = PostRequest(
                        teacher = "BBdfyjkdls",
                        subject = "salkjdlakjsl",
                        student = "SUHALFJAK"
                    )

                    ApiClient.apiService.createPost(post).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                Log.d("Retrofit", "Post успешен")
                            } else {
                                Log.e("Retrofit", "Ошибка на сервере: ${response.code()}")
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Log.e("Retrofit", "Ошибка запроса: ${t.message}")
                        }
                    })

                }
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                BottomDrawer(icons, pagerState)
            }
        }
    }
}