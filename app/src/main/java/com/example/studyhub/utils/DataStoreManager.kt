package com.example.studyhub.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.practiceplacement.utils.PrefKeys
import com.example.practiceplacement.utils.dataStore
import kotlinx.coroutines.flow.first

class DataStoreManager(private val context: Context) {

    suspend fun saveAfterConfirm(place: String) {
        context.dataStore.edit { prefs ->
            prefs[PrefKeys.STATUS] = "Подтвержден"
            prefs[PrefKeys.PLACE] = place
        }
    }

    suspend fun saveAfterLogin(id: String, isLogin: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PrefKeys.ID] = id
            prefs[PrefKeys.IS_LOGIN] = isLogin.toString()
        }
    }

    suspend fun clearData() {
        context.dataStore.edit { prefs ->
            prefs.remove(PrefKeys.ID)
            prefs.remove(PrefKeys.IS_LOGIN)
            prefs.remove(PrefKeys.PLACE)
            prefs.remove(PrefKeys.HEAD_TEACHER)
            prefs.remove(PrefKeys.STATUS)
            prefs.remove(PrefKeys.SUPERVISOR)
            prefs.remove(PrefKeys.TOPIC)
        }
    }

    suspend fun getLogin(): Boolean {
        val prefs = context.dataStore.data.first()
        val idString = prefs[PrefKeys.ID] ?: return false
        return idString.toIntOrNull() != null
    }

    suspend fun getTeacher(): String {
        val prefs = context.dataStore.data.first()
        return prefs[PrefKeys.HEAD_TEACHER] ?: ""
    }

    suspend fun getStatus(): String {
        val prefs = context.dataStore.data.first()
        return prefs[PrefKeys.STATUS] ?: ""
    }

    suspend fun getPlace(): String {
        val prefs = context.dataStore.data.first()
        return prefs[PrefKeys.PLACE] ?: ""
    }

    suspend fun getId(): Int {
        val prefs = context.dataStore.data.first()
        val idString = prefs[PrefKeys.ID] ?: return -1
        return idString.toIntOrNull() ?: -1
    }

    suspend fun getTopic(): String {
        val prefs = context.dataStore.data.first()
        return prefs[PrefKeys.TOPIC] ?: ""
    }

    suspend fun saveTopic(topic: String) {
        context.dataStore.edit { prefs ->
            prefs[PrefKeys.TOPIC] = topic
        }
    }

    suspend fun getSupervisor(): String {
        val prefs = context.dataStore.data.first()
        return prefs[PrefKeys.SUPERVISOR] ?: ""
    }

    suspend fun saveSupervisor(supervisor: String) {
        context.dataStore.edit { prefs ->
            prefs[PrefKeys.SUPERVISOR] = supervisor
        }
    }

}
