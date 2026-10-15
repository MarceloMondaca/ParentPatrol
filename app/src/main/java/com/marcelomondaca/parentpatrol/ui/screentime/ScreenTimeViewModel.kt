
package com.marcelomondaca.parentpatrol.ui.screentime

// -------------------------------------------------
// ANDROID Y VIEWMODEL
// -------------------------------------------------

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

// -------------------------------------------------
// ESTADOS DE JETPACK COMPOSE
// -------------------------------------------------

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

// -------------------------------------------------
// DATASTORE Y CORRUTINAS
// -------------------------------------------------

import com.marcelomondaca.parentpatrol.data.ScreenTimePreferences
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Gestiona el tiempo de uso permitido
 * para cada perfil infantil.
 */
class ScreenTimeViewModel(
    application: Application
) : AndroidViewModel(application) {

    // Acceso a las preferencias de tiempo.
    private val screenTimePreferences = ScreenTimePreferences(application)

    // Trabajo encargado de observar DataStore.
    private var minutesJob: Job? = null

    // Identificador del perfil seleccionado.
    private var selectedChildId: Long? = null

    // Minutos observables desde la interfaz.
    var selectedMinutes by mutableStateOf<Int?>(null)
        private set

    // -------------------------------------------------
    // 1. CARGAR TIEMPO DEL PERFIL
    // -------------------------------------------------

    // Consulta el tiempo del niño seleccionado.
    fun loadChildTime(childId: Long) {

        // Evita consultar nuevamente el mismo perfil.
        if (selectedChildId == childId) return

        // Cancela la consulta del perfil anterior.
        minutesJob?.cancel()

        // Guarda el ID del niño seleccionado.
        selectedChildId = childId

        // Limpia los minutos del perfil anterior.
        selectedMinutes = null

        // Observa los minutos individuales.
        minutesJob = viewModelScope.launch {

            screenTimePreferences
                .selectedMinutesFlow(childId)
                .collect { minutes ->

                    // Actualiza el tiempo en pantalla.
                    selectedMinutes = minutes

                }

        }

    } // Fin de loadChildTime.

    // -------------------------------------------------
    // 2. GUARDAR TIEMPO DEL PERFIL
    // -------------------------------------------------

    // Guarda los minutos del niño seleccionado.
    fun saveSelectedMinutes(minutes: Int) {

        // Comprueba que exista un perfil seleccionado.
        val childId = selectedChildId ?: return

        // Actualiza inmediatamente la interfaz.
        selectedMinutes = minutes

        viewModelScope.launch {

            // Guarda los minutos individuales en DataStore.
            screenTimePreferences.saveSelectedMinutes(
                childId = childId,
                minutes = minutes
            )

        }

    } // Fin de saveSelectedMinutes.

} // Fin de ScreenTimeViewModel.
