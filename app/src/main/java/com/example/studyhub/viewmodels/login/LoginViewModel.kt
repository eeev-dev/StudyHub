package com.example.studyhub.viewmodels.login

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyhub.data.remote.repository.login.AuthRepository
import com.example.studyhub.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val dataStoreManager = DataStoreManager(appContext)

    var isLogin by mutableStateOf(false)

    var studentNumber by mutableStateOf("")
        private set

    var term by mutableStateOf("")
        private set

    var loginResult by mutableStateOf<LoginResult?>(null)
        private set

    var message by mutableStateOf("")

    fun onStudentNumberChange(new: String) {
        studentNumber = new
    }

    fun getIsLogin() {
        viewModelScope.launch {
            isLogin = dataStoreManager.getLogin()
        }
    }

    fun login() {
        viewModelScope.launch {
            try {
                val result = repository.login(studentNumber)
                result.onSuccess { response ->
                    if (response.id != null) {
                        dataStoreManager.saveAfterLogin(
                            response.id.toString(),
                            response.term.toString(),
                            true
                        )
                        loginResult = LoginResult.Success(response.message)
                    }
                }.onFailure {
                    loginResult = LoginResult.Error(it.message ?: "Неизвестная ошибка")
                }
            } catch (e: Exception) {
                loginResult = LoginResult.Error("Сервер недоступен: ${e.message}")
            }
        }
    }

    sealed class LoginResult {
        data class Success(val message: String) : LoginResult()
        data class Error(val message: String) : LoginResult()
    }
}