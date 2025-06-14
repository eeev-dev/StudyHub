package com.example.studyhub.viewmodels.vkr

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyhub.data.local.dao.ScheduleDao
import com.example.studyhub.data.remote.repository.vkr.GraduateRepository
import com.example.studyhub.data.remote.repository.vkr.TeacherRepository
import com.example.studyhub.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupervisorViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository,
    private val graduateRepository: GraduateRepository,
    @ApplicationContext private val appContext: Context,
    private val scheduleDao: ScheduleDao
) : ViewModel() {

    private val _teachers = MutableStateFlow<List<String>>(emptyList())
    val teachers: StateFlow<List<String>> = _teachers

    init {
        fetchPlaces()
    }

    private fun fetchPlaces() {
        viewModelScope.launch {
            val result = teacherRepository.getTeachers()
            if (result.isSuccess) {
                _teachers.value = result.getOrNull() ?: emptyList()
            } else {
                Log.e("SelectionViewModel", "Ошибка при загрузке", result.exceptionOrNull())
            }
        }
    }

    private val dataStoreManager = DataStoreManager(appContext)

    var postResult by mutableStateOf<Boolean?>(null)
        private set

    fun postSupervisor(name: String) {
        viewModelScope.launch {
            val res = graduateRepository.postSupervisor(dataStoreManager.getId().toString(), name)
            res.onSuccess {
                postResult = it.success
                Toast.makeText(appContext, "Ожидайте подтверждения", Toast.LENGTH_SHORT).show()
            }
            res.onFailure {
                val message = it.message
                Toast.makeText(appContext, "Ошибка: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            scheduleDao.clearAll()
            dataStoreManager.clearData()
        }
    }
}