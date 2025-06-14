package com.example.studyhub.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first

class DataStoreManager(private val context: Context) {

    suspend fun saveAfterLogin(id: String, isLogin: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PrefKeys.ID] = id
            prefs[PrefKeys.IS_LOGIN] = isLogin.toString()
            prefs[PrefKeys.IS_REMIND] = "true"
        }
    }

    suspend fun getId(): Int {
        val prefs = context.dataStore.data.first()
        val idString = prefs[PrefKeys.ID] ?: return -1
        return idString.toIntOrNull() ?: -1
    }

    suspend fun saveIsRemind(isRemind: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PrefKeys.IS_REMIND] = isRemind.toString()
        }
    }

    suspend fun getIsRemind(): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[PrefKeys.IS_REMIND]?.toBoolean() ?: false
    }

    suspend fun getLogin(): Boolean {
        val prefs = context.dataStore.data.first()
        val idString = prefs[PrefKeys.ID] ?: return false
        return idString.toIntOrNull() != null
    }

    suspend fun saveTeacher(teacher: String) {
        context.dataStore.edit { prefs ->
            prefs[PrefKeys.HEAD_TEACHER] = teacher
        }
    }

    suspend fun getTeacher(): String {
        val prefs = context.dataStore.data.first()
        return prefs[PrefKeys.HEAD_TEACHER] ?: ""
    }

    suspend fun saveStatus(status: String) {
        context.dataStore.edit { prefs ->
            prefs[PrefKeys.STATUS] = status
        }
    }

    suspend fun getStatus(): String {
        val prefs = context.dataStore.data.first()
        return prefs[PrefKeys.STATUS] ?: ""
    }

    suspend fun savePlace(place: String) {
        context.dataStore.edit { prefs ->
            prefs[PrefKeys.PLACE] = place
        }
    }

    suspend fun getPlace(): String {
        val prefs = context.dataStore.data.first()
        return prefs[PrefKeys.PLACE] ?: ""
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

}
