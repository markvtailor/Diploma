package com.markvtls.diploma.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserInfoStore(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    suspend fun saveUserEmail(userEmail: String) {
        dataStore.edit { preferences ->
            preferences[USER_EMAIL] = userEmail
        }
    }

    suspend fun saveUserPhone(userPhone: String) {
        dataStore.edit { preferences ->
            preferences[USER_PHONE] = userPhone
        }
    }

    val userEmailFlow: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else throw it
        }.map { preferences ->
            preferences[USER_EMAIL] ?: ""
        }

    val userPhoneFlow: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else throw it
        }.map { preferences ->
            preferences[USER_PHONE] ?: ""
        }

    companion object {
        private const val USER_INFO = "USER_INFO"
        private val USER_PHONE = stringPreferencesKey("user_phone")
        private val USER_EMAIL = stringPreferencesKey("user_string")

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = USER_INFO
        )
    }
}