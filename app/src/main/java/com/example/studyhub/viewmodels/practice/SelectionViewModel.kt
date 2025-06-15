package com.example.studyhub.viewmodels.practice

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyhub.data.local.dao.ReminderDao
import com.example.studyhub.data.local.dao.ScheduleDao
import com.example.studyhub.data.remote.api.intern.InternResponse
import com.example.studyhub.data.remote.models.Place
import com.example.studyhub.data.remote.repository.intern.CompaniesRepository
import com.example.studyhub.data.remote.repository.intern.InternRepository
import com.example.studyhub.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectionViewModel @Inject constructor(
    private val companiesRepository: CompaniesRepository,
    private val internRepository: InternRepository,
    @ApplicationContext private val appContext: Context,
    private val scheduleDao: ScheduleDao,
    private val reminderDao: ReminderDao
) : ViewModel() {

    private val dataStoreManager = DataStoreManager(appContext)

    var intern by mutableStateOf<InternResponse?>(null)
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

    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> = _places

    init {
        getIntern()
        fetchPlaces()
    }

    private fun fetchPlaces() {
        viewModelScope.launch {
            val result = companiesRepository.getCompanies()
            if (result.isSuccess) {
                _places.value = result.getOrNull() ?: emptyList()
            } else {
                Log.e("SelectionViewModel", "Ошибка при загрузке", result.exceptionOrNull())
            }
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