package com.example.studyhub.viewmodels.exam

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyhub.data.local.dao.ReminderDao
import com.example.studyhub.data.local.dao.ScheduleDao
import com.example.studyhub.data.remote.api.exam.ExamApi
import com.example.studyhub.data.remote.api.exam.ExamApi.ExamRequest
import com.example.studyhub.data.remote.repository.vkr.GraduateRepository
import com.example.studyhub.data.remote.repository.vkr.TeacherRepository
import com.example.studyhub.utils.DataStoreManager
import com.example.studyhub.utils.openFile
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.io.File
import javax.inject.Inject
import kotlin.jvm.java

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val api: ExamApi,
    @ApplicationContext private val appContext: Context,
    private val scheduleDao: ScheduleDao,
    private val reminderDao: ReminderDao
) : ViewModel() {

    private val dataStoreManager = DataStoreManager(appContext)

    fun downloadDocx() {
        viewModelScope.launch {
            val studentId = dataStoreManager.getId()

            if (studentId != -1) {
                val response = api.getExams(ExamRequest(studentId.toString()))

                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        val inputStream = body.byteStream()
                        val file = File(appContext.filesDir, "exam_schedule.docx")
                        file.outputStream().use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                        val uri = FileProvider.getUriForFile(
                            appContext,
                            "${appContext.packageName}.provider",
                            file
                        )

                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            setDataAndType(uri, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }

                        try {
                            appContext.startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(appContext, "Нет приложения для открытия .docx файлов", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    val errorText = response.errorBody()?.string()
                    val obj = Gson().fromJson(errorText, ErrorResponse::class.java)
                    Toast.makeText(appContext, "Ошибка: ${obj.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    data class ErrorResponse(
        val success: Boolean,
        val message: String
    )

    fun logout() {
        viewModelScope.launch {
            scheduleDao.clearAll()
            reminderDao.clearAll()
            dataStoreManager.clearData()
        }
    }
}