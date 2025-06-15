package com.example.studyhub.viewmodels.practice

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyhub.data.local.dao.ReminderDao
import com.example.studyhub.data.local.dao.ScheduleDao
import com.example.studyhub.data.remote.models.Place
import com.example.studyhub.data.remote.models.Review
import com.example.studyhub.data.remote.repository.intern.CompaniesRepository
import com.example.studyhub.data.remote.repository.intern.InternRepository
import com.example.studyhub.data.remote.repository.intern.ReviewRepository
import com.example.studyhub.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val companiesRepository: CompaniesRepository,
    private val internRepository: InternRepository,
    private val reviewRepository: ReviewRepository,
    @ApplicationContext private val appContext: Context,
    private val scheduleDao: ScheduleDao,
    private val reminderDao: ReminderDao
) : ViewModel() {

    var place: Place? = null

    private val dataStoreManager = DataStoreManager(appContext)

    fun getPlace(placeId: Int) = viewModelScope.launch {
        val result = companiesRepository.getCompanies()
        if (result.isSuccess) {
            val list = result.getOrNull()
            place = list?.find { it.id == placeId }
        } else {
            Toast.makeText(appContext, "Ошибка: ${result.isFailure}", Toast.LENGTH_SHORT).show()
        }
    }

    var postResult by mutableStateOf<Boolean?>(null)
        private set

    fun sendPlace(placeId: Int) {
        viewModelScope.launch {
            val id = dataStoreManager.getId()
            if (id != -1) {
                val res = internRepository.sendIntern(id, placeId)
                if (res.isSuccess) {
                    postResult = true
                    Toast.makeText(appContext, "Ожидайте подтверждения", Toast.LENGTH_SHORT).show()
                }
                if (res.isFailure) {
                    postResult = false
                    Toast.makeText(appContext, res.toString(), Toast.LENGTH_SHORT).show()
                }
            } else Toast.makeText(appContext, "Пользователь не найден. Войдите снова", Toast.LENGTH_SHORT).show()
        }
    }

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews

    fun getReviews(placeId: Int) = viewModelScope.launch {
        val result = reviewRepository.getReviews(placeId)
        if (result.isSuccess) {
            _reviews.value = result.getOrNull() ?: emptyList()
        } else {
            Toast.makeText(appContext, "Ошибка: ${result.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
            println("Ошибка: ${result.exceptionOrNull()?.message}")
        }
    }

    fun logout() {
        viewModelScope.launch {
            scheduleDao.clearAll()
            reminderDao.clearAll()
            dataStoreManager.clearData()
        }
    }
}