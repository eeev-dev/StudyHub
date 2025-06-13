package com.example.studyhub.viewmodels.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyhub.data.remote.api.login.PingResponse
import com.example.studyhub.data.remote.repository.login.PingRepository
import com.example.studyhub.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(
    private val pingRepository: PingRepository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val dataStoreManager = DataStoreManager(appContext)

    var isLogin by mutableStateOf<Boolean>(false)
        private set

    var message by mutableStateOf("")

    fun isLogin() {
        viewModelScope.launch {
            isLogin = dataStoreManager.getLogin()
        }
    }

    var response by mutableStateOf<PingResponse?>(null)
        private set

    fun startServer() {
        viewModelScope.launch {
            val result = pingRepository.ping("1")
            result.onSuccess {
                response = it
            }.onFailure {
                message = it.message.toString()
                if (message != "timeout")
                    Toast.makeText(
                        appContext,
                        it.message ?: "Неизвестная ошибка",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }
}