package com.example.studyhub.viewmodels.schedule

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.studyhub.data.local.dao.PlanDao
import com.example.studyhub.data.local.dao.ScheduleDao
import com.example.studyhub.data.local.entities.PlanEntity
import com.example.studyhub.data.local.entities.ScheduleEntity
import com.example.studyhub.data.remote.api.intern.InternResponse
import com.example.studyhub.data.remote.models.Place
import com.example.studyhub.data.remote.models.Schedule
import com.example.studyhub.data.remote.repository.intern.CompaniesRepository
import com.example.studyhub.data.remote.repository.intern.InternRepository
import com.example.studyhub.data.remote.repository.schedule.ScheduleRepository
import com.example.studyhub.utils.DataStoreManager
import com.example.studyhub.utils.toEntity
import com.example.studyhub.utils.toSchedule
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleDao: ScheduleDao,
    private val planDao: PlanDao,
    private val repository: ScheduleRepository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {


    private val dataStoreManager = DataStoreManager(appContext)

    private val _schedule = MutableStateFlow<List<Schedule>>(emptyList())
    val schedule: StateFlow<List<Schedule>> = _schedule

    var term by mutableStateOf<String>("")
        private set

    fun getTerm() {
        viewModelScope.launch {
            term = dataStoreManager.getTerm()
        }
    }

    init {
        getTerm()
    }

    fun initSchedule() {
        viewModelScope.launch {
            val scheduleList = scheduleDao.getAll()
            try {
                scheduleList
                    .catch { e -> Log.e("ImagesViewModel", "Ошибка при загрузке данных из Room", e) }
                    .distinctUntilChanged()
                    .collect { itemsList ->
                        if (itemsList.isEmpty()) {
                            fetchSchedule()
                        }
                        else {
                            if (itemsList.none { it.term == term }) fetchSchedule()
                            else _schedule.value = itemsList.map { it.toSchedule() }
                        }
                    }
            } catch (e: Exception) {
                Log.e("ImagesViewModel", "Ошибка в collect", e)
            }
        }
    }

    fun addPlan(plan: PlanEntity) {
        viewModelScope.launch {
            planDao.insert(plan)
        }
    }

    private fun fetchSchedule() {
        viewModelScope.launch {
            val studentId = dataStoreManager.getId()
            val term = dataStoreManager.getTerm()
            print(studentId)
            val result = repository.getSchedule(studentId.toString(), term)
            if (result.isSuccess) {
                _schedule.value = result.getOrNull() ?: emptyList()
                scheduleDao.insertAll(_schedule.value.map { it.toEntity() })
            } else {
                Log.e("SelectionViewModel", "Ошибка при загрузке", result.exceptionOrNull())
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.clearData()
        }
    }
}
