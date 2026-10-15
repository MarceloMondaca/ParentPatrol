
package com.marcelomondaca.parentpatrol.data

// -------------------------------------------------
// ANDROID Y DATASTORE
// -------------------------------------------------

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

// -------------------------------------------------
// FLUJOS DE DATOS
// -------------------------------------------------

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// -------------------------------------------------
// ALMACENAMIENTO DE PREFERENCIAS
// -------------------------------------------------

// DataStore utilizado para guardar los tiempos.
private val Context.screenTimeDataStore by preferencesDataStore(
    name = "screen_time_settings"
)

/**
 * Administra el tiempo de uso permitido
 * para cada perfil infantil.
 */
class ScreenTimePreferences(
    private val context: Context
) {

    // -------------------------------------------------
    // 1. CLAVE INDIVIDUAL DEL PERFIL
    // -------------------------------------------------

    // Genera una clave utilizando el ID del niño.
    private fun minutesKey(childId: Long) =
        intPreferencesKey("selected_minutes_$childId")

    // -------------------------------------------------
    // 2. GUARDAR TIEMPO DE USO
    // -------------------------------------------------

    // Guarda los minutos del niño seleccionado.
    suspend fun saveSelectedMinutes(
        childId: Long,
        minutes: Int
    ) {

        context.screenTimeDataStore.edit { preferences ->

            // Guarda el tiempo con su clave individual.
            preferences[minutesKey(childId)] = minutes

        }

    } // Fin de saveSelectedMinutes.

    // -------------------------------------------------
    // 3. CONSULTAR TIEMPO DE USO
    // -------------------------------------------------

    // Observa los minutos del niño seleccionado.
    fun selectedMinutesFlow(childId: Long): Flow<Int?> {

        return context.screenTimeDataStore.data.map { preferences ->

            // Recupera el tiempo de este perfil.
            preferences[minutesKey(childId)]

        }

    } // Fin de selectedMinutesFlow.

} // Fin de ScreenTimePreferences.
