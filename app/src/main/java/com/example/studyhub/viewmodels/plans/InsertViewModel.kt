package com.example.studyhub.viewmodels.plans

import android.app.AlarmManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyhub.data.local.dao.PlanDao
import com.example.studyhub.data.local.dao.ReminderDao
import com.example.studyhub.data.local.dao.ScheduleDao
import com.example.studyhub.data.local.entities.PlanEntity
import com.example.studyhub.data.remote.repository.schedule.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsertViewModel @Inject constructor(
    private val planDao: PlanDao,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    fun addPlan(plan: PlanEntity) {
        viewModelScope.launch { planDao.insert(plan) }
    }

    fun deletePlan(plan: PlanEntity) {
        viewModelScope.launch { planDao.delete(plan) }
    }

    fun completePlan(plan: PlanEntity) {
        viewModelScope.launch {
            val updatedPlan = plan.copy(isFinished = true)
            planDao.insert(updatedPlan)
        }
    }
}