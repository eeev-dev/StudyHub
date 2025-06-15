package com.example.studyhub.viewmodels.vkr

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyhub.data.local.dao.ReminderDao
import com.example.studyhub.data.local.dao.ScheduleDao
import com.example.studyhub.data.remote.api.vkr.GraduateResponse
import com.example.studyhub.data.remote.repository.vkr.GraduateRepository
import com.example.studyhub.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VkrViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val repository: GraduateRepository,
    private val reminderDao: ReminderDao,
    private val scheduleDao: ScheduleDao
) : ViewModel() {
    private val dataStoreManager = DataStoreManager(appContext)

    var text by mutableStateOf<String>("")
        private set

    var graduate by mutableStateOf<GraduateResponse?>(null)
        private set

    var isRefreshing = mutableStateOf(false)

    fun refresh() {
        viewModelScope.launch {
            if (saved == null) {
                isRefreshing.value = true
                graduate = null
                getGraduate()
                isRefreshing.value = false
            }
        }
    }

    data class Info(
        var supervisor: String,
        var topic: String
    )

    var saved by mutableStateOf<Info?>(null)
        private set

    init {
        getInfo()
        getText()
    }

    fun getInfo() {
        viewModelScope.launch {
            val topic = dataStoreManager.getTopic()
            val supervisor = dataStoreManager.getSupervisor()
            if (supervisor != "" && topic != "") {
                saved = Info(supervisor = supervisor, topic = topic)
            }
            else getGraduate()
        }
    }

    fun getGraduate() {
        viewModelScope.launch {
            val studentId = dataStoreManager.getId()

            if (studentId == -1) {
                Toast.makeText(appContext, "Пользователь не найден", Toast.LENGTH_SHORT).show()
            } else {
                val result = repository.get(studentId.toString())
                result.onSuccess {
                    graduate = it
                    if (it.status == "Проверка пройдена") {
                        dataStoreManager.saveTopic(it.topic)
                        dataStoreManager.saveSupervisor(it.supervisor)
                        graduate = null
                    }
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

    fun getText() {
        viewModelScope.launch {
            text = dataStoreManager.getTopic()
        }
    }

    fun saveTopic(topic: String) {
        viewModelScope.launch {
            dataStoreManager.saveTopic(topic)
            Toast.makeText(appContext, "Сохранено", Toast.LENGTH_SHORT).show()
        }
    }

    fun postTopic(title: String) {
        viewModelScope.launch {
            val res = repository.postTopic(dataStoreManager.getId().toString(), title)
            res.onSuccess {
                Toast.makeText(appContext, "Ожидайте подтверждения", Toast.LENGTH_SHORT).show()
                refresh()
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
            reminderDao.clearAll()
            dataStoreManager.clearData()
        }
    }
}