package com.example.studyhub.utils

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_prefs")

object PrefKeys {
    val ID = stringPreferencesKey("student_id")
    val TERM = stringPreferencesKey("term")

    val STATUS = stringPreferencesKey("status")
    val HEAD_TEACHER = stringPreferencesKey("head_teacher")
    val PLACE = stringPreferencesKey("place")
    val IS_LOGIN = stringPreferencesKey("is_login")

    val SUPERVISOR = stringPreferencesKey("supervisor")
    val TOPIC = stringPreferencesKey("topic")
}

