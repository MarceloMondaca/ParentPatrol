package com.marcelomondaca.parentpatrol.data

import android.content.Context // Permite acceder al almacenamiento de la aplicación.
import androidx.datastore.preferences.preferencesDataStore // Crea un DataStore de preferencias.
import androidx.datastore.preferences.core.booleanPreferencesKey // Crea claves para valores Boolean.
import androidx.datastore.preferences.core.edit // Permite modificar las preferencias.
import kotlinx.coroutines.flow.Flow // Representa datos que pueden cambiar con el tiempo.
import kotlinx.coroutines.flow.map // Permite transformar los datos recibidos.

private val Context.dataStore by preferencesDataStore(
    name = "protection_settings" // Nombre del archivo de preferencias.
)

class ProtectionPreferences(
    private val context: Context // Recibe el contexto de Android.

) {
    private object PreferencesKeys {
        val BLOCK_ADULT_CONTENT = booleanPreferencesKey("block_adult_content") // Clave para contenido adulto.
        val BLOCK_INAPPROPRIATE_APPS = booleanPreferencesKey("block_inappropriate_apps") // Clave para aplicaciones.
    }
    suspend fun saveAdultContentBlocking(enabled: Boolean) { // Guarda el estado del bloqueo adulto.
        context.dataStore.edit { preferences -> // Abre las preferencias para modificarlas.
            preferences[PreferencesKeys.BLOCK_ADULT_CONTENT] = enabled // Guarda true o false.
        }
    }

    suspend fun saveInappropriateAppsBlocking(enabled: Boolean) { // Guarda el bloqueo de aplicaciones.
        context.dataStore.edit { preferences -> // Abre las preferencias para modificarlas.
            preferences[PreferencesKeys.BLOCK_INAPPROPRIATE_APPS] = enabled // Guarda true o false.
        }
    }

    val blockAdultContentFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences -> // Lee las preferencias almacenadas.
            preferences[PreferencesKeys.BLOCK_ADULT_CONTENT] ?: false // Devuelve el valor o false.
        }

    val blockInappropriateAppsFlow: Flow<Boolean> = context.dataStore.data
       .map { preferences -> // Lee las preferencias almacenadas.
           preferences[PreferencesKeys.BLOCK_INAPPROPRIATE_APPS] ?: false // Devuelve el valor o false.
       }
}
