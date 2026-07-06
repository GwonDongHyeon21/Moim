package com.moim.data.common.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun saveTokens(
        accessToken: String,
        refreshToken: String
    ) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
            preferences[REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun clearTokens() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    val accessTokenFlow = dataStore.data.map { preferences ->
        preferences[ACCESS_TOKEN]
    }

    val refreshTokenFlow = dataStore.data.map { preferences ->
        preferences[REFRESH_TOKEN]
    }

    fun getAccessTokenSync() = runBlocking {
        dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }.first()
    }

    fun getRefreshTokenSync() = runBlocking {
        dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN]
        }.first()
    }

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }
}