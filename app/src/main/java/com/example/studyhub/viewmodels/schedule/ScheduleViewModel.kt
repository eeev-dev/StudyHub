package com.example.studyhub.viewmodels.schedule

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyhub.data.local.dao.ReminderDao
import com.example.studyhub.reminder.Utils
import com.example.studyhub.data.local.dao.ScheduleDao
import com.example.studyhub.data.local.entities.ReminderEntity
import com.example.studyhub.data.remote.models.Schedule
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
import java.util.Calendar
import javax.inject.Inject
import kotlin.String

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleDao: ScheduleDao,
    private val reminderDao: ReminderDao,
    private val repository: ScheduleRepository,
    private val alarmManager: AlarmManager,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val dataStoreManager = DataStoreManager(appContext)

    private val _schedule = MutableStateFlow<List<Schedule>>(emptyList())
    val schedule: StateFlow<List<Schedule>> = _schedule

    init {
        initSchedule()
    }

    fun initSchedule() {
        viewModelScope.launch {
            val scheduleList = scheduleDao.getAll()

            try {
                scheduleList
                    .catch { e ->
                        Log.e(
                            "ImagesViewModel",
                            "Ошибка при загрузке данных из Room",
                            e
                        )
                    }
                    .distinctUntilChanged()
                    .collect { itemsList ->
                        if (itemsList.isEmpty()) {
                            fetchSchedule()
                        } else {
                            _schedule.value = itemsList.map { it.toSchedule() }
                            val isRemind = dataStoreManager.getIsRemind()
                            if (isRemind) {
                                _schedule.value.forEach { schedule ->
                                    if (schedule.is_online == 1) {
                                        if (schedule.day != null && schedule.time != null && schedule.id != null) {
                                            scheduleNotification(
                                                schedule.day.toString(),
                                                schedule.time.take(5),
                                                "Нажмите, чтобы зайти на пару \uD83D\uDD25",
                                                schedule.id,
                                                schedule.link ?: ""
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
            } catch (e: Exception) {
                Log.e("ImagesViewModel", "Ошибка в collect", e)
            }
        }
    }

    fun addPlan(title: String, content: String) {
        /*val plan = PlanEntity(
            title = title,
            content = content,
            deadline = ,
            isFinished = false
        )
        viewModelScope.launch {
            planDao.insert(plan)
        }*/
    }

    private fun fetchSchedule() {
        viewModelScope.launch {
            val studentId = dataStoreManager.getId()
            val result = repository.getSchedule(studentId.toString())
            if (result.isSuccess) {
                _schedule.value = result.getOrNull() ?: emptyList()
                scheduleDao.insertAll(_schedule.value.map { it.toEntity() })
            } else {
                Log.e("SelectionViewModel", "Ошибка при загрузке", result.exceptionOrNull())
            }
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    fun scheduleNotification(
        day: String,
        time: String,
        text: String,
        id: Int,
        url: String
    ) {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()

            val dayOfWeek = when (day) {
                "Пн" -> Calendar.MONDAY
                "Вт" -> Calendar.TUESDAY
                "Ср" -> Calendar.WEDNESDAY
                "Чт" -> Calendar.THURSDAY
                "Пт" -> Calendar.FRIDAY
                "Сб" -> Calendar.SATURDAY
                "Вс" -> Calendar.SUNDAY
                else -> return@launch
            }

            val (hour, minute) = time.split(":").map { it.toInt() }

            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek)

            if (calendar.timeInMillis < System.currentTimeMillis()) {
                calendar.add(Calendar.WEEK_OF_YEAR, 1)
            }

            val baseTriggerTime = calendar.timeInMillis

            // Устанавливаем напоминания на 26 недель (полгода)
            for (weekOffset in 0 until 26) {
                val triggerTime =
                    baseTriggerTime + weekOffset * 7L * 24 * 60 * 60 * 1000 // смещение на неделю в миллисекундах

                val pendingIntent = Utils.getPendingContent(appContext, id + weekOffset, text, url)
                reminderDao.insert(ReminderEntity((id + weekOffset).toString(), text, url))

                // id+weekOffset — чтобы PendingIntent был уникальным для каждой недели

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S || alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
                }
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
