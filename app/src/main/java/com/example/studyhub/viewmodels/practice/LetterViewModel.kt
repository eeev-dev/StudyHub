package com.example.studyhub.viewmodels.practice

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyhub.data.remote.api.intern.InternResponse
import com.example.studyhub.data.remote.api.intern.LetterApi
import com.example.studyhub.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class LetterViewModel @Inject constructor(
    private val api: LetterApi,
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    private val dataStoreManager = DataStoreManager(appContext)

    var result by mutableStateOf<Result<InternResponse>?>(null)
        private set

    fun clearResult() {
        result = null
    }

    fun sendPlace(imageFile: File) {
        viewModelScope.launch {
            val internId = dataStoreManager.getId()
            if (internId != -1) {
                try {
                    val requestFile = imageFile
                        .asRequestBody("image/*".toMediaTypeOrNull())

                    val body = MultipartBody.Part.createFormData(
                        "image", imageFile.name, requestFile
                    )

                    result = api.sendLetter(body, internId)
                } catch (e: Exception) {
                    result = Result.failure(e)
                }
            } else {
                result = Result.failure(Exception("intern_id не найден"))
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.clearData()
        }
    }
}