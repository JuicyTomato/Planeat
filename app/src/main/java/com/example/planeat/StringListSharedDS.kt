/*package com.example.planeat

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "shared")      //diverso tra dataStores

class StringListSharedDS(context: Context) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    companion object {
        val STRING_LIST_KEY = stringPreferencesKey("shared_list")       //diverso tra dataStores
    }

    suspend fun addStringToList(string: String) {
        dataStore.edit { preferences ->
            val currentList = preferences[STRING_LIST_KEY]?.split(",")?.toMutableList() ?: mutableListOf()
            currentList.add(string)
            preferences[STRING_LIST_KEY] = currentList.joinToString(",")
        }
    }

    suspend fun removeStringFromList(string: String) {
        dataStore.edit { preferences ->
            val currentList = preferences[STRING_LIST_KEY]?.split(",")?.toMutableList() ?: mutableListOf()
            currentList.remove(string)
            preferences[STRING_LIST_KEY] = currentList.joinToString(",")
        }
    }

    fun getStringList(): Flow<List<String>> {
        return dataStore.data.map { preferences ->
            val stringListString = preferences[STRING_LIST_KEY] ?: ""
            stringListString.split(",")
        }
    }
}

 */


