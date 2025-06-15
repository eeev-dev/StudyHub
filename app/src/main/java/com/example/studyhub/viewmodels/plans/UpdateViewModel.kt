package com.example.studyhub.viewmodels.plans

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyhub.data.local.dao.PlanDao
import com.example.studyhub.data.local.entities.PlanEntity
import com.example.studyhub.data.remote.models.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val planDao: PlanDao,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    var plan by mutableStateOf<PlanEntity?>(null)
        private set

    fun getPlan(id: Int) {
        viewModelScope.launch {
            plan = planDao.getPlan(id)
        }
    }

    fun updatePlan(plan: PlanEntity, title: String, content: String, deadline: String) {
        viewModelScope.launch {
            val updatedPlan = plan.copy(title = title, content = content, deadline = deadline)
            planDao.insert(updatedPlan)
        }
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