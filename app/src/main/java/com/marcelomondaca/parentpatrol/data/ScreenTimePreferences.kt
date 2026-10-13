package com.marcelomondaca.parentpatrol.data

import android.content.Context // Permite acceder al contexto de Android.
import androidx.datastore.preferences.preferencesDataStore // Crea un DataStore de preferencias.
import androidx.datastore.preferences.core.intPreferencesKey // Crea claves para guardar números enteros.
import androidx.datastore.preferences.core.edit // Permite modificar las preferencias.
import kotlinx.coroutines.flow.Flow // Representa datos que pueden cambiar.
import kotlinx.coroutines.flow.map // Permite transformar los datos recibidos.

private val Context.screenTimeDataStore by preferencesDataStore(
    name = "screen_time_settings" // Archivo donde guardaremos el tiempo permitido.
)

class ScreenTimePreferences(
    private val context: Context // Recibe el contexto para acceder a DataStore.
)
 {

    private object PreferencesKeys {
        val SELECTED_MINUTES = intPreferencesKey("selected_minutes") // Clave para guardar los minutos.
    }

    suspend fun saveSelectedMinutes(minutes: Int) { // Guarda el tiempo seleccionado.
        context.screenTimeDataStore.edit { preferences -> // Abre las preferencias para modificarlas.
            preferences[PreferencesKeys.SELECTED_MINUTES] = minutes // Guarda los minutos seleccionados.
        }
    }

     val selectedMinutesFlow: Flow<Int?> = context.screenTimeDataStore.data
         .map { preferences -> // Lee las preferencias almacenadas.
             preferences[PreferencesKeys.SELECTED_MINUTES] // Recupera los minutos guardados.
         }
}