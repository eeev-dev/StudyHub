package com.example.studyhub.viewmodels.plans

import android.app.AlarmManager
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyhub.data.local.dao.PlanDao
import com.example.studyhub.data.local.dao.ReminderDao
import com.example.studyhub.data.local.dao.ScheduleDao
import com.example.studyhub.data.local.entities.PlanEntity
import com.example.studyhub.data.remote.models.Schedule
import com.example.studyhub.data.remote.repository.schedule.ScheduleRepository
import com.example.studyhub.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlansViewModel @Inject constructor(
    private val planDao: PlanDao,
    private val scheduleDao: ScheduleDao,
    private val reminderDao: ReminderDao,
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    private val _plans = MutableStateFlow<List<PlanEntity>>(emptyList())
    val plans: StateFlow<List<PlanEntity>> = _plans

    private val dataStoreManager = DataStoreManager(appContext)

    init {
        fetchPlans()
    }

    fun fetchPlans() {
        viewModelScope.launch {
            val planList = planDao.getPlans()
            planList
                .catch { e -> Log.e("ImagesViewModel", "Ошибка при загрузке данных из Room", e) }
                .distinctUntilChanged()
                .collect { itemsList ->
                    if (!itemsList.isEmpty()) {
                        _plans.value = itemsList
                    }
                }
        }
    }

    fun deletePlan(plan: PlanEntity) {
        viewModelScope.launch { planDao.delete(plan) }
    }

    fun logout() {
        viewModelScope.launch {
            scheduleDao.clearAll()
            reminderDao.clearAll()
            dataStoreManager.clearData()
        }
    }
}