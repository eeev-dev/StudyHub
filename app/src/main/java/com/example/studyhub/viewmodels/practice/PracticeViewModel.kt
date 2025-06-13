package com.example.studyhub.viewmodels.practice

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyhub.data.remote.api.intern.InternResponse
import com.example.studyhub.data.remote.api.intern.ReviewResponse
import com.example.studyhub.data.remote.api.vkr.GraduateResponse
import com.example.studyhub.data.remote.repository.intern.InternRepository
import com.example.studyhub.data.remote.repository.intern.ReviewRepository
import com.example.studyhub.data.remote.repository.vkr.GraduateRepository
import com.example.studyhub.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.onSuccess

@HiltViewModel
class PracticeViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val internRepository: InternRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val dataStoreManager = DataStoreManager(appContext)

    var intern by mutableStateOf<InternResponse?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var isRefreshing = mutableStateOf(false)

    fun refresh() {
        viewModelScope.launch {
            isRefreshing.value = true
            intern = null
            getIntern()
            isRefreshing.value = false
        }
    }

    init {
        getIntern()
        getPlaceTitle()
    }

    fun getPlaceTitle() {
        viewModelScope.launch {
            title = dataStoreManager.getPlace()
        }
    }

    fun getIntern() {
        viewModelScope.launch {
            val studentId = dataStoreManager.getId()

            if (studentId == -1) {
                Toast.makeText(appContext, "Пользователь не найден", Toast.LENGTH_SHORT).show()
            } else {
                val result = internRepository.getIntern(studentId)
                result.onSuccess {
                    intern = it
                }.onFailure {
                    Toast.makeText(
                        appContext,
                        it.message ?: "Неизвестная ошибка",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private val _resultReview = MutableLiveData<Result<ReviewResponse>>()

    fun sendReview(rating: Int, text: String, date: String, placeId: Int) {
        viewModelScope.launch {
            val res = reviewRepository.sendReview(rating, text, date, placeId)
            _resultReview.value = res
            res.onSuccess {
                val message = it.message
                Toast.makeText(appContext, "Ответ сервера: $message", Toast.LENGTH_SHORT).show()
                Log.d("ReviewVM", "Ответ сервера: $message")
            }
            res.onFailure {
                val message = it.message
                Toast.makeText(appContext, "Ответ сервера: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.clearData()
        }
    }
}